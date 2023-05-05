# EDJC

[[**Homepage**](https://ed7n.github.io/edjc)]

## Building

    $ find src/eden/common/ -iname '*.java' -printf '%p\n' > files && javac -d release --release 8 --source-path src @files && jar -c -f release/edjc.jar -m res/MANIFEST.MF -C release eden

## Formatting

    $ prettier --write '**/*.java'
