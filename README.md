
# Virtual Store

by Bardia Parmoun and Guy Morgenshtern

Released on: 2/05/2021

## DESCRIPTION
- This program simulates a virtual cooking store.
- Each client can choose from the available items and at the end the program will generate a receipt for them.
- It can support different number of clients (the default number is 3)

## RUNNING THE PROGRAM
- Note that you need to have java installed on your computer in order to run the program. The latest version of JDK can 
be found here: https://adoptopenjdk.net/releases.html
- To run the program you can use either use StoreView or StoreViewBatchUI:
- You need to compile the program:

Locate the mystore folder and inside of that run the following command: 
```shell
$ javac sources.txt
```
Then exit the folder and run either of the follwing commands to access the UIs:
```shell
$ java mystore/StoreView
```
or 
```shell
$ java mystore/StoreViewBatchUI
```
## USING THE PROGRAM
- After running StoreView you will prompted by the following window:
<p align="center">
The main window for the program
<img src="images/main_page.JPG" />
</p>

- You can add and remove any item to your cart by clicking either the + or but if you try to add or remove an illegal you will be prompted with the following error message: 
<p align="center">
<img src="images/illegal_amount.JPG" />
</p>

- You can view the items in your cart by clicking the View Cart button:
<p align="center">
An example of the message displayed by view cart
<img src="images/view_cart.JPG" />
</p>

- You can also empty you cart at any time using the empty cart button which displays the following message:
<p align="center">
<img src="images/empty_cart.JPG" />
</p>
## CREDITS
Author: Bardia Parmoun & Guy Morgenshtern

Copyright © 2021 Bardia Parmoun Guy Morgenshtern. All rights reserved
