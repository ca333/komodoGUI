# [Komodo](https://komodoplatform.com/) Desktop GUI Wallet - for Windows
[disclaimer](#disclaimer)

Komodo for Windows is not yet officially supported.
Thanks to [fadeddreamz](https://github.com/radix42) an experimental build has been provided.
Before installing the GUI wallet on Windows you need to install ZCash on Windows.

![Screenshot](https://github.com/ca333/komodo-swing-wallet-ui/raw/master/docs/komodo_gui.png "Komodo Wallet")

1. Installing Komodo on Windows

   The easiest way to install it is to use the binary version provided by [@radix42](https://github.com/radix42):

   1.1. Download the ZCash node [ZCash-v1.0.5.zip](https://zcash.dl.mercerweiss.com/zcash-v1.0.5-rc1.zip)

   1.2. Unzip the file so that the executables `komodod.exe` and `komodo-cli.exe` are in one directory.

   1.3. Download the [ZCash Proving Key](https://z.cash/downloads/sprout-proving.key)
        and store it in directory `%APPDATA%\ZcashParams`.

   1.4. Download the [ZCash Verifying Key](https://z.cash/downloads/sprout-verifying.key)
        and store it in directory `%APPDATA%\ZcashParams`.

   After downloading the two keys, they should be available in the same directory similar to:
![Screenshot](https://github.com/ca333/komodo-swing-wallet-ui/raw/master/docs/ZCashKeyDir.png "ZCash keys directory on Windows")

   1.5. Create directory `%APPDATA%\Komodo` and a text file `komodo.conf` in it with the following content:
   ```
   rpcuser=username
   rpcpassword=SomePassword_PleaseChangeThisValue   
   ```

2. Installing the Komodo Desktop GUI wallet

   2.1. Install JDK 8 for Windows - e.g. [use this good tutorial](http://www.wikihow.com/Install-the-Java-Software-Development-Kit)

   2.2. You may use the [latest binary release of the ZCash Desktop GUI wallet](https://github.com/ca333/komodo-swing-wallet-ui/releases/latest).
   Download file [ZCashSwingWalletUI.jar](https://github.com/ca333/komodo-swing-wallet-ui/releases/download/0.58-beta/ZCashSwingWalletUI.jar)
   and place it in the same folder as `komodod.exe` and `komodo-cli.exe`. The result needs to be similar to:
![Screenshot](https://github.com/ca333/komodo-swing-wallet-ui/raw/master/docs/ZCashWinDir.png "ZCash directory on Windows")

4. Running the installed ZCash GUI wallet

   The wallet GUI is a Java program packaged as an executable JAR file. It may be run from command line or started from another GUI tool
   (e.g. file manager). One way to run it from command line is:
   ```
   java -jar KomodoSwingWalletUI.jar
   ```
   You may instead just use Windows the file manager and double-click on the `KomodoSwingWalletUI.jar`.
   This will start the ZCash GUI wallet.

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

1. Limitation: Wallet encryption has been temporarily disabled in ZCash due to stability problems. A corresponding issue
[#1552](https://github.com/zcash/zcash/issues/1552) has been opened by the ZCash developers. Correspondingly
wallet encryption has been temporarily disabled in the ZCash Desktop GUI Wallet.
1. Issue: the GUI wallet does not work correctly if komodod is started with a custom data directory, like:
`komodod -datadir=/home/data/whatever` This will be fixed in later versions.
1. Issue: GUI data tables (transactions/addresses etc.) allow copying of data via double click but also allow editing.
The latter needs to be disabled.
1. Limitation: The list of transactions does not show all outgoing ones (specifically outgoing Z address
transactions). A corresponding issue [#1438](https://github.com/zcash/zcash/issues/1438) has been opened
for the ZCash developers.
