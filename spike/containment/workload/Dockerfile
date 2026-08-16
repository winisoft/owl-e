FROM python:3.12-slim

# ca-certificates provides update-ca-certificates, used to install the gateway CA into
# the system trust store. requests/certifi is the client under test.
# iproute2 is here only to install a default route pointing at the gateway container.
# Docker gives an internal network no default route at all, which means raw-IP traffic
# dies in the local kernel with ENETUNREACH and is never observed by anything.
RUN apt-get update \
 && apt-get install -y --no-install-recommends ca-certificates iproute2 \
 && rm -rf /var/lib/apt/lists/*

RUN pip install --no-cache-dir requests==2.32.3 certifi

COPY probe.py /probe.py
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
