#!/bin/bash
sqlite3 ./src/db/sdis2.db < ./src/db/sdis2.sql
sqlite3 ./src/db/sdis2.db < ./src/db/populate.sql
