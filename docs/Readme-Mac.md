# [Komodo](https://komodoplatform.com/) Desktop GUI Wallet - for Mac OS X
[disclaimer](#disclaimer)

Komodo for Mac OS is not yet officially supported. This experimental build has been provided by [ca333](https://github.com/ca333).
Before installing the GUI wallet on Mac OS you need to [install Komodo on Mac OS](https://github.com/jl777/komodo/tree/darwin).
Please follow the link for this. In case of any problems you may contact the developer for help ;-)
For the rest of this document we assume that you have a Komodo node (komodod) running on Mac OS...
You can get the prebuilt OSX binaries (komodod/komodo-cli) [here](https://github.com/ca333/komodo/releases/tag/v1.0.5-osx).

![Screenshot](https://github.com/ca333/komodo-swing-wallet-ui/raw/master/docs/komodo_gui.png "Komodo GUI on Mac")

1. Build tools

   You need to install git, JDK 8 and Ant for Mac OS to build the GUI wallet. The commands
   `git`, `java`, `javac` and `ant` need to be startable from command line before proceeding with
   build. The procedure could be:

   1.1. [Install homebrew](http://brew.sh/)

   1.2. Install git: `brew install git`

   1.3. [Install JDK 8](https://docs.oracle.com/javase/8/docs/technotes/guides/install/mac_jdk.html)

   1.4. [Install Ant](http://www.admfactory.com/how-to-install-apache-ant-on-mac-os-x/)

2. Building the Komodo GUI wallet

   Version 0.37 (beta) or later needs to be built from source. The build procedure is the same as on Linux.
   Summary of commands:
   ```
   git clone https://github.com/ca333/komodo-swing-wallet-ui.git
   cd komodo-swing-wallet-ui/
   ant -buildfile ./src/build/build.xml
   chmod u+x ./build/jars/KomodoSwingWalletUI.jar
   ```
   At this point the build process is finished the built GUI wallet program is the JAR
   file `./build/jars/KomodoSwingWalletUI.jar`

3. Installing the built Komodo GUI wallet

   Assuming you have already built from source code [Komodo](https://komodoplatform.com/) in directory `/Users/joe/komodo/src` (for
   example) you need to take the created file `./build/jars/KomodoSwingWalletUI.jar` and copy it
   to directory `/Users/joe/komodo/src` (the same dir. that contains `komodo-cli` and `komodod`). Example copy command:
   ```
   cp ./build/jars/KomodoSwingWalletUI.jar /Users/joe/komodo/src    
   ```

4. Running the installed Komodo GUI wallet

   Before running the GUI you need to start komodod (e.g. `komodod --daemon`). The wallet GUI is a Java program packaged
   as an executable JAR file. It may be run from command line or started from another GUI tool (e.g. file manager).
   Assuming you have already installed [Komodo](https://komodoplatform.com/) and the GUI Wallet `KomodoSwingWalletUI.jar` in
   directory `/Users/joe/komodo/src` one way to run it from command line is:
   ```
   java -jar /Users/joe/komodo/src/KomodoSwingWalletUI.jar
   ```
   You may instead just use Mac the file manager and double-click on the `KomodoSwingWalletUI.jar`.
   This will start the Komodo GUI wallet.

### Donations accepted
At the present time this project is non-commercial in nature and developed by volunteers. If you find the GUI
Wallet useful, please consider making a donation for its further development. Your contribution matters! Donations
are accepted at Komodo address:
```
RCA333bLBdgzvJmHFAwCBBHUwwCsTVatp9
```

### License
This program is distributed under an [MIT License](https://github.com/ca333/komodo-swing-wallet-ui/raw/master/LICENSE).

### Disclaimer
This program is not officially endorsed by or associated with the ZCash project and the ZCash company.
[ZCash®](https://trademarks.justia.com/871/93/zcash-87193130.html) and the
[ZCash® logo](https://trademarks.justia.com/868/84/z-86884549.html) are trademarks of the
[Zerocoin Electric Coin Company](https://trademarks.justia.com/owners/zerocoin-electric-coin-company-3232749/).

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

### Known issues and limitations

1. Limitation: Wallet encryption has been temporarily disabled in ZCash (which is the base of komodo) due to stability problems. A corresponding issue
[#1552](https://github.com/zcash/zcash/issues/1552) has been opened by the ZCash developers. Correspondingly
wallet encryption has been temporarily disabled in the Komodo Desktop GUI Wallet.
1. Issue: the GUI wallet does not work correctly if komodod is started with a custom data directory, like:
`komodod -datadir=/home/data/whatever` This will be fixed in later versions.
1. Issue: GUI data tables (transactions/addresses etc.) allow copying of data via double click but also allow editing.
The latter needs to be disabled.
1. Limitation: The list of transactions does not show all outgoing ones (specifically outgoing Z address
transactions). A corresponding issue [#1438](https://github.com/zcash/zcash/issues/1438) has been opened
for the ZCash developers.
1. Limitation: The CPU percentage shown to be taken by komodod on Linux is the average for the entire lifetime
of the process. This is not very useful. This will be improved in future versions.
