#!/bin/sh
set -e

# The capture sidecar.
#
# Runs from the gateway image but inside the WORKLOAD's network namespace, which is the
# only vantage point that sees packets the sealed bridge will later discard. A raw-IP
# connection never reaches the gateway container — it is dropped by the host before it
# gets there — so without this, an evasion attempt would be contained but invisible.
#
# The workload cannot switch this off: separate container, separate PID and mount
# namespaces. It shares the network namespace and nothing else.

rm -f /out/tap.txt /out/tap.ready

echo "[tap] capturing on workload namespace"
tcpdump -i any -n -U -l 'tcp and not port 53' > /out/tap.txt 2>/dev/null &
TCPDUMP_PID=$!

# Signal readiness only once capture is actually live, so the workload doesn't race ahead
# and make its attempts before anything is watching.
sleep 2
touch /out/tap.ready
echo "[tap] ready"

wait "$TCPDUMP_PID"
