# EDJC

[[**Homepage**](https://ed7n.github.io/edjc)]

## Building

    $ find src/eden/common/ -name *.java > files && javac -d release --release 8 --source-path src @files && jar -c -f release/edjc.jar -m res/MANIFEST.MF -C release eden
