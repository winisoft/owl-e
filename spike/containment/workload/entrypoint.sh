#!/bin/sh
set -e

GATEWAY_IP=172.28.0.10
CA=/ca/mitmproxy-ca-cert.pem

# Point everything at the sentinel, including traffic that never asked DNS a question.
#
# On an internal Docker network there is no default route, so a connection to a literal IP
# fails instantly in the local kernel (ENETUNREACH) and no packet is ever emitted — contained,
# but invisible. Routing the default via the gateway container means those packets are handed
# to the sentinel, which can log them before refusing them. Containment is unchanged: the
# gateway has no route out either.
echo "[workload] routing default via $GATEWAY_IP"
ip route del default 2>/dev/null || true
ip route add default via "$GATEWAY_IP"

# Don't start probing until the capture sidecar is actually watching, or the raw-IP
# attempts would happen before anything could observe them and the result would be a
# false negative rather than a finding.
echo "[workload] waiting for capture sidecar"
i=0
while [ ! -f /out/tap.ready ]; do
    i=$((i + 1))
    if [ "$i" -gt 60 ]; then
        echo "[workload] capture sidecar never signalled ready" >&2
        exit 1
    fi
    sleep 1
done

echo "[workload] waiting for gateway CA"
i=0
while [ ! -f "$CA" ]; do
    i=$((i + 1))
    if [ "$i" -gt 60 ]; then
        echo "[workload] CA never appeared at $CA" >&2
        exit 1
    fi
    sleep 1
done

# Keep a pristine copy of the stock trust bundle BEFORE we touch it. probe.py uses it as a
# negative control: the same request against the untouched bundle must fail. Without that,
# a green "TLS verified" result could just mean verification was never really happening.
cp "$(python -c 'import certifi; print(certifi.where())')" /tmp/pristine-ca.pem

# Install into BOTH trust stores. The system store covers curl/openssl; certifi's bundle
# is what Python's requests actually reads. Doing it this way means the environment stays
# clean — no REQUESTS_CA_BUNDLE, no SSL_CERT_FILE, nothing for the workload to notice.
echo "[workload] installing gateway CA into system store + certifi bundle"
cp "$CA" /usr/local/share/ca-certificates/owle-gateway.crt
update-ca-certificates >/dev/null 2>&1
cat "$CA" >> "$(python -c 'import certifi; print(certifi.where())')"

exec python /probe.py
