#!/bin/bash

set -e

psql -U postgres << EOF
CREATE DATABASE billing OWNER postgres;
EOF