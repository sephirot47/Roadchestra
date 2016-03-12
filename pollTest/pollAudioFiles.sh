#!/bin/bash

LAST_POLLED_FILE=""
while true; do
	OLDER_FILE=$(ls -lt | head -n2 | tail -n1 | awk '{print $9}')
	if [ ! "$LAST_POLLED_FILE" = "$OLDER_FILE" ] 
	then
		LAST_POLLED_FILE=$OLDER_FILE
		F=$OLDER_FILE
		echo $F
	fi
done
