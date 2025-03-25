#!/bin/sh

INITSCRIPTS_DIR=/etc/init.d


case "$1" in
start)
echo "Starting init script for Module Loading"
start-stop-daemon -S -n init -a $INITSCRIPTS_DIR/scull_load
;;
stop)
echo "Removing user modules"
start-stop-daemon -K -n scull_load
start-stop-daemon -S -n init -a $INITSCRIPTS_DIR/scull_unload
;;
*)
echo "Usage: $0 {start|stop}"
exit 1
esac
exit 0

