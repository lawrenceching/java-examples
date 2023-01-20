#!/bin/bash

#!/bin/bash
#mvn clean package -Pnative -DskipTests
upx --lzma --best ./target/native -o ./target/native.upx