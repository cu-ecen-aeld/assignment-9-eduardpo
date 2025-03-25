#!/bin/sh

INITSCRIPTS_DIR=/etc/init.d

case "$1" in
start)
echo "Starting init script for Module Loading"
start-stop-daemon -S -n init -a $INITSCRIPTS_DIR/module_load -- faulty
start-stop-daemon -S -n init -a $INITSCRIPTS_DIR/module_load -- hello
;;
stop)
echo "Removing user modules"
start-stop-daemon -K -n module_load
start-stop-daemon -S -n init -a $INITSCRIPTS_DIR/module_unload -- faulty
start-stop-daemon -S -n init -a $INITSCRIPTS_DIR/module_unload -- hello
;;
*)
echo "Usage: $0 {start|stop}"
exit 1
esac
exit 0

