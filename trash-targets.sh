#!/bin/bash

for dir in *
do
	if [[ -d "$dir" ]]; then
		trash $dir/target
	fi
done

