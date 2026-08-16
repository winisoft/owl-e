#!/bin/sh
set -e

echo "[gateway] installing transparent redirect rules"
# Two kinds of traffic arrive here:
#   1. hostname traffic, which DNS pointed at us, addressed to this container
#   2. raw-IP traffic, addressed to somewhere else entirely, which reaches us only
#      because the workload's default route points at this container
#
# REDIRECT happens in PREROUTING, before the routing decision, so both are pulled into
# the local mitmproxy listener regardless of what address they were aimed at.
#
# Every TCP port is captured, not just 80/443 — an exfil attempt on port 9999 should be
# just as visible as one on 443. DNS over TCP is excepted so dnsmasq keeps working.
iptables -t nat -A PREROUTING -p tcp --dport 53 -j RETURN
iptables -t nat -A PREROUTING -p tcp -j REDIRECT --to-port 8080

echo "[gateway] starting dnsmasq (wildcard resolver)"
dnsmasq -k -C /etc/dnsmasq.conf &

mkdir -p /out /ca

echo "[gateway] starting mitmdump (transparent, offline cert minting)"
# --set upstream_cert=false is load-bearing: the default behaviour is to contact the
# real server to copy its certificate before minting one, and on a sealed network
# there is no real server to contact. This mints from the TLS SNI alone.
exec mitmdump \
    --mode transparent \
    --listen-host 0.0.0.0 \
    --listen-port 8080 \
    --set confdir=/ca \
    --set upstream_cert=false \
    --set connection_strategy=lazy \
    --set termlog_verbosity=info \
    --set flow_detail=0 \
    -s /addon.py
