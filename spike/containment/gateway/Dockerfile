FROM python:3.12-slim

# tcpdump is used by the capture sidecar, which runs from this same image inside the
# workload's network namespace to observe packets the sealed bridge later discards.
RUN apt-get update \
 && apt-get install -y --no-install-recommends dnsmasq iptables tcpdump \
 && rm -rf /var/lib/apt/lists/*

RUN pip install --no-cache-dir mitmproxy==11.0.2

COPY dnsmasq.conf /etc/dnsmasq.conf
COPY addon.py /addon.py
COPY fixtures.json /fixtures.json
COPY entrypoint.sh /entrypoint.sh
COPY tap-entrypoint.sh /tap-entrypoint.sh
RUN chmod +x /entrypoint.sh /tap-entrypoint.sh

EXPOSE 53/udp 80 443

ENTRYPOINT ["/entrypoint.sh"]
