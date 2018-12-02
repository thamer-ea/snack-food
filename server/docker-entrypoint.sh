#!/bin/sh
set -e

if [ "$1" = 'snack-food' ]; then
    shift;
    # allows passing arguments to java
    # in the "docker run" command
    exec java -jar snack-food.jar "$@"
fi

exec "$@"