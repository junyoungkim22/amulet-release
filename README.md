## Getting Started

Install the following packages.
```
sudo apt-get -y update
sudo apt-get -y install gdb
sudo apt install -y build-essential
sudo apt-get install -y manpages-dev
sudo apt-get install -y clang-format
sudo apt install -y openjdk-17-jdk openjdk-17-jre
sudo add-apt-repository -y ppa:deadsnakes/ppa
sudo apt install -y python3.9
sudo apt install -y python-is-python3
```

Create main directory for Amulet and enter it.
```
mkdir amulet
cd amulet
```

Download Java with jvmci.
```
wget https://github.com/graalvm/graal-jvmci-8/releases/download/jvmci-20.3-b12/openjdk-8u292+05-jvmci-20.3-b12-linux-amd64.tar.gz
tar -xf openjdk-8u292+05-jvmci-20.3-b12-linux-amd64.tar.gz
```
Clone and enter adaptive-code-generation.
```
git clone https://github.com/junyoungkim22/amulet-release.git
cd amulet-release
```

In app.config, set 'arch' to SIMD instruction set supported by machine (avx, avx2, avx512).

In env_var_set.sh, set PROJECT_HOME to path to the main directory created earlier (e.g. /home/username/amulet) and then set environment variables by running script.
```
source env_var_set.sh
```

Clone mx.
```
cd $PROJECT_HOME
git clone https://github.com/graalvm/mx.git
cd mx
git checkout abebc30
```

Clone and build graal-simd.
```
cd $PROJECT_HOME
git clone https://github.com/junyoungkim22/graal-simd.git
cd graal-simd/compiler
mx build
```

Clone and build graaljs.
```
cd $PROJECT_HOME
git clone https://github.com/oracle/graaljs.git
cd graaljs
git checkout 98055d37c3
cd graal-js
mx build
```
Compile native code for packing matrices, and build Amulet.
```
cd $PROJECT_HOME
cd amulet-release
./scripts/compile_native.sh
./scripts/fast_build.sh
```

Execute query using Amulet.
```
./scripts/speed_run.sh tests/release_tests/matmul_decl.js 1
```
