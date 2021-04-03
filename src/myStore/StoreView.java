//Bardia Parmoun 101143006
//Guy Morgenshtern 101151430

package myStore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import java.awt.Font;

/**
 * The class that assigns each user a store view and keeps track of all the storeviews
 * @author Guy Morgenshtern 101151430 & Bardia Parmoun 101143006
 * @version 3.0
 * @date 2020/03/30
 */
public class StoreView {
    /**
     * Keeps track of the main frame for the store
     */
    private final JFrame FRAME;

    /**
     * Keeps track of the main store using StoreManager
     */
    private StoreManager storeManager;

    /**
     * Keeps track of the cartid
     */
    private int cartId;

    /**
     * Keeps track of the panels for all of the products
     */
    private ArrayList<JPanel> productPanels;

    /**
     * Keeps track of the labels for all of the products
     */
    private HashMap<Product, JLabel> productInfoLabels;

    private JLabel cartTotalLabel;


    /**
     * The default constructor for storeview which creates an instance of a user
     * @param storeManager the manager of the store
     * @param cartId the id of the  cart
     */
    public StoreView(StoreManager storeManager, int cartId) {
        this.storeManager = storeManager;
        this.cartId = cartId;
        this.FRAME = new JFrame();
        this.productPanels = new ArrayList<>();
        this.productInfoLabels = new HashMap<>();
        this.cartTotalLabel = new JLabel();
    }

    /**
     * Adds the item to the cart
     * @return JButton : a JButton object.
     */
    private JButton getAddToCartButton(Product product) {
        JButton button = new JButton("+");
        button.setSize(50,30);

        // add the action listener
        button.addActionListener(new ActionListener() {

            // this method will be called when we click the button
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!addToCartUI(product.getName(), 1)){
                    JOptionPane.showMessageDialog(FRAME, "Illegal amount!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    productInfoLabels.get(product).setText("Price: $" + product.getPrice() + " Stock:" +
                            storeManager.checkStock(product));
                    cartTotalLabel.setText("Cart total: $" +
                            Math.round(storeManager.getCartTotalPrice(cartId) * 100.0)/100.0);
                }
            }
        });
        return button;
    }

    /**
     * Removes the item from the cart
     * @return JButton : a JButton object.
     */
    private JButton getRemoveFromCartButton(Product product) {
        JButton button = new JButton("-");
        button.setSize(50,30);

        // add the action listener
        button.addActionListener(new ActionListener() {

            // this method will be called when we click the button
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!removeFromCartUI(product.getName(), 1)){
                    JOptionPane.showMessageDialog(FRAME, "Illegal amount!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    productInfoLabels.get(product).setText("Price: $" + product.getPrice() + " Stock:" +
                            storeManager.checkStock(product));
                    cartTotalLabel.setText("Cart total: $" +
                            Math.round(storeManager.getCartTotalPrice(cartId) * 100.0)/100.0);
                }
            }
        });

        return button;
    }

    /**
     * Shows the contents of the cart
     * @return JButton : a JButton object.
     */
    private JButton getViewCartButton(Icon icon) {
        JButton button = new JButton("view cart", icon);
        button.setSize(50,30);

        // add the action listener
        button.addActionListener(new ActionListener() {

            // this method will be called when we click the button
            @Override
            public void actionPerformed(ActionEvent ae) {
                displayCart();
            }
        });

        return button;
    }

    /**
     * Creates the button that empties the cart
     * @return the empty cart button
     */
    private JButton getEmptyCartButton() {
        JButton button = new JButton("empty cart");
        button.setSize(50,30);

        // add the action listener
        button.addActionListener(new ActionListener() {

            // this method will be called when we click the button
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (JOptionPane.showConfirmDialog(FRAME, "Are you sure you want to empty your cart?")
                        == JOptionPane.OK_OPTION) {
                    // close it down!
                    storeManager.emptyCart(cartId);
                    for (Product p: productInfoLabels.keySet()) {
                        productInfoLabels.get(p).setText("Price: $" + p.getPrice() + " Stock:" +
                                storeManager.checkStock(p));
                    }
                    cartTotalLabel.setText("Cart total: $" +
                            Math.round(storeManager.getCartTotalPrice(cartId) * 100.0)/100.0);
                }

            }
        });

        return button;
    }

    /**
     * Creates that checkout button
     * @return the checkout the buttom
     */
    private JButton getCheckoutButton() {
        JButton button = new JButton("checkout");
        button.setSize(50,50);

        // add the action listener
        button.addActionListener(new ActionListener() {

            // this method will be called when we click the button
            @Override
            public void actionPerformed(ActionEvent ae) {
                    displayOrder();
                };
        });
        return button;
    }

    /**
     * Creates the quit button
     * @return the quit button
     */
    private JButton getQuitButton() {
        JButton button = new JButton("quit");
        button.setSize(50,30);

        // add the action listener
        button.addActionListener(new ActionListener() {

            // this method will be called when we click the button
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (JOptionPane.showConfirmDialog(FRAME, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    // close it down!
                    storeManager.emptyCart(cartId);
                    FRAME.setVisible(false);
                    FRAME.dispose();
                }

            }
        });

        return button;
    }

    /**
     * The main method that lets the users interact with the store
     * @param args used to get arguments when running the program (not used in this program)
     */
    public static void main(String[] args){
        StoreManager sm = new StoreManager();

        // The number of allowed active users
        int activeSV = 1;

        StoreView[] customers = new StoreView[activeSV];

        for (int i=0; i<activeSV; i++){
            customers[i] = new StoreView(sm, sm.assignNewCartID());
        }

        customers[0].displayGUI();
    }

    /**
     * Calls the main UI for the program to allow for different items to be called
     * @return true if the UI finished successfully
     */
    public boolean displayGUI(){
        FRAME.setTitle("Guy and Bardia's Pie and Media!");
        FRAME.setMinimumSize(new Dimension(800,700));

        int numProducts = storeManager.getAvailableProducts().size();

        // Creating the JPanels
        JPanel headerPanel = new JPanel();
        JPanel productPanel = new JPanel(new GridLayout(numProducts/2,2));
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JPanel menuPanel = new JPanel();
        JPanel productPanelWithSpace = new JPanel();
        JPanel footerPanel = new JPanel();

        // Creating the JLabels
        Font fontTitle = new Font("Comic Sans Ms", Font.BOLD + Font.ITALIC, 14);
        JLabel headerLabel = new JLabel("Welcome to Guy and Bardia's Pie and Media! (ID: " + cartId + ")");
        headerLabel.setFont(fontTitle);

        // set the preferred sizes and colours
        headerPanel.setPreferredSize(new Dimension(500, 50));
        productPanel.setPreferredSize(new Dimension(500, 700));
        productPanelWithSpace.setPreferredSize(new Dimension(550, 700));
        menuPanel.setPreferredSize(new Dimension(300, 700));
        buttonPanel.setPreferredSize(new Dimension(250, 150));
        footerPanel.setPreferredSize(new Dimension(500, 50));

        // adding JLabel to the header panel
        headerPanel.add(headerLabel,BorderLayout.CENTER);

        // adding the empty cart button to the footer panel
        footerPanel.add(getEmptyCartButton());

        // creating the labels for all the products
        displayItems();

        // adding all the products to the productPanel
        for (JPanel p: productPanels){
            productPanel.add(p);
        }

        // adding spacing to the product panel
        productPanelWithSpace.add(productPanel);

        // creating the menu panel
        try {
            // adding the buttons
            // adding the cart total label
            cartTotalLabel = new JLabel("Cart total: $" +
                    Math.round(storeManager.getCartTotalPrice(cartId) * 100.0)/100.0);

            GridBagConstraints gbagConstraintsTotal = new GridBagConstraints();
            gbagConstraintsTotal.gridx = 1;
            gbagConstraintsTotal.gridy = 0;
            gbagConstraintsTotal.insets = new Insets(5,5,5,5);
            buttonPanel.add(cartTotalLabel, gbagConstraintsTotal);

            // adding the view cart button
            BufferedImage img = ImageIO.read(getClass().getResource("images/shoppingcart.png"));
            Image dimg = img.getScaledInstance(30,30, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(dimg);
            GridBagConstraints gbagConstraintsCart = new GridBagConstraints();
            gbagConstraintsCart.gridx = 1;
            gbagConstraintsCart.gridy = 1;
            gbagConstraintsCart.insets = new Insets(5,5,5,5);
            buttonPanel.add(getViewCartButton(icon), gbagConstraintsCart);

            // adding the checkout button
            GridBagConstraints gbagConstraintsCheckout = new GridBagConstraints();
            gbagConstraintsCheckout.gridx = 1;
            gbagConstraintsCheckout.gridy = 2;
            gbagConstraintsCheckout.insets = new Insets(5,5,5,5);
            buttonPanel.add(getCheckoutButton(), gbagConstraintsCheckout);

            // adding the quit button
            GridBagConstraints gbagConstraintsQuit = new GridBagConstraints();
            gbagConstraintsQuit.gridx = 1;
            gbagConstraintsQuit.gridy = 3;
            gbagConstraintsQuit.insets = new Insets(5,5,5,5);
            buttonPanel.add(getQuitButton(), gbagConstraintsQuit);

            // creating the menu panel with proper spacing
            menuPanel.add(buttonPanel, BorderLayout.CENTER);

            // adding a border to the menu panel
            TitledBorder title = BorderFactory.createTitledBorder("");
            buttonPanel.setBorder(title);
        }

        catch (IOException e){
            JOptionPane.showMessageDialog(FRAME, "Could not load the image", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // adding scroll bars to main
        JScrollPane productPanelWithScroll = new JScrollPane(productPanelWithSpace,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // adding all the components to the frame
        FRAME.add(headerPanel,BorderLayout.NORTH);
        FRAME.getContentPane().add(productPanelWithScroll,BorderLayout.CENTER);
        FRAME.add(menuPanel,BorderLayout.EAST);
        FRAME.add(footerPanel,BorderLayout.SOUTH);

        // add the window listener
        FRAME.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        FRAME.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (JOptionPane.showConfirmDialog(FRAME, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    // close it down!
                    FRAME.setVisible(false);
                    FRAME.dispose();
                }
            }
        });

        // making the frame visible
        FRAME.setVisible(true);

        // pack
        FRAME.pack();

        return true;
    }

    /**
     * Displays the removefromcart interface where the user is prompted to choose a product and a proper amount which
     * is then removed from the cart
     */
    public boolean removeFromCartUI(String product, int amount){
        return storeManager.removeFromCart(product, amount, cartId);
    }

    /**
     * Displays the addToCart interface where the user is prompted to choose a product and a proper amount which is then
     * added to the cart
     */
    public boolean addToCartUI(String product, int amount){
        return storeManager.addToCart(product, amount, cartId);
    }

    /**
     * Displays all the available items in the store
     */
    public void displayItems() {
        String name;
        String stock;
        String price;

        int width = 150;
        int height = 150;

        for (int i = 0; i < storeManager.getAvailableProducts().size(); i++) {
            try {
                Product p = storeManager.getAvailableProducts().get(i);
                name = p.getName();
                stock = String.valueOf(storeManager.checkStock(p));
                price = String.valueOf(p.getPrice());

                //the label for the image
                JLabel productLabel = new JLabel();
                productLabel.setPreferredSize(new Dimension(width,height));

                //loading the image
                BufferedImage img = ImageIO.read(getClass().getResource(
                        storeManager.getAvailableProducts().get(i).getImagePath()));

                //resizing the image
                Image dimg = img.getScaledInstance(width,height, Image.SCALE_SMOOTH);

                //adding the image to the label
                ImageIcon icon = new ImageIcon(dimg);
                productLabel.setIcon(icon);

                //adding the label to the panel
                JPanel productPanel = new JPanel(new GridBagLayout());
                productPanel.setPreferredSize(new Dimension(width,height+100));

                JPanel buttonPanel = new JPanel();

                JLabel productInfo = new JLabel("Price: $" + price + " Stock:" + stock);

                // adding the label to the product labels hashmap
                productInfoLabels.put(p,productInfo);

                // adding the productinfo label
                GridBagConstraints gbagConstraintsInfo = new GridBagConstraints();
                gbagConstraintsInfo.gridx = 0;
                gbagConstraintsInfo.gridy = 0;
                productPanel.add(productInfo, gbagConstraintsInfo);

                // adding the image for the product
                GridBagConstraints gbagConstraintsRightImage = new GridBagConstraints();
                gbagConstraintsRightImage.gridx = 0;
                gbagConstraintsRightImage.gridy = 1;
                productPanel.add(productLabel, gbagConstraintsRightImage);

                // adding the addtocart and removefromcart buttons
                buttonPanel.add(getRemoveFromCartButton(p));
                buttonPanel.add(getAddToCartButton(p));

                GridBagConstraints gbagConstraintsButtons= new GridBagConstraints();
                gbagConstraintsButtons.gridx = 0;
                gbagConstraintsButtons.gridy = 2;
                productPanel.add(buttonPanel, gbagConstraintsButtons);

                //adding the border
                TitledBorder title = BorderFactory.createTitledBorder(name + " (ID: " + p.getId() +")");
                productPanel.setBorder(title);

                productPanels.add(productPanel);
            }
            catch(IOException e){
                JOptionPane.showMessageDialog(FRAME, "Could not load the image" , "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Displays all the available items in the cart
     */
    public void displayCart() {
        String name;
        String stock;
        String price;
        String printMsg;

        boolean isCartEmpty = true;

        String cartText;
        cartText =  "Stock             Product                Price\n";
        cartText += "__________________________________________________\n";

        for (Product p : storeManager.getCartProducts(cartId).keySet()) {
            if (storeManager.getCartProducts(cartId).get(p)>0){
                isCartEmpty = false;
                name = p.getName();
                stock = String.valueOf(storeManager.getCartProducts(cartId).get(p));
                price = String.valueOf(p.getPrice());

                printMsg = String.format("%s" + "%" + (15 - stock.length() + name.length()) + "s" + "%" +
                        (25 - name.length() + price.length()) + "s", stock, name, price);

                cartText += printMsg+"\n";
            }
        }

        if (isCartEmpty){
            cartText += "Cart is empty\n";
        }

        cartText += "__________________________________________________\n";
        cartText+="Cart total: $" + Math.round(storeManager.getCartTotalPrice(cartId) * 100.0)/100.0;

        JOptionPane.showMessageDialog(FRAME, cartText, "Your cart!", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays a summary of the order
     */
    public void displayOrder() {
        String name;
        String stock;
        String price;
        String printMsg;

        boolean isCartEmpty = true;

        String cartText = "Are you sure you want to checkout?\n\n Order summary: \n\n";

        cartText +=  "Stock             Product                Price\n";
        cartText += "__________________________________________________\n";

        for (Product p : storeManager.getCartProducts(cartId).keySet()) {
            if (storeManager.getCartProducts(cartId).get(p)>0){
                isCartEmpty = false;
                name = p.getName();
                stock = String.valueOf(storeManager.getCartProducts(cartId).get(p));
                price = String.valueOf(p.getPrice());

                printMsg = String.format("%s" + "%" + (15 - stock.length() + name.length()) + "s" + "%" +
                        (25 - name.length() + price.length()) + "s", stock, name, price);

                cartText += printMsg+"\n";
            }
        }

        if (isCartEmpty){
            cartText += "You didn't order anything :(\n";
        }


        cartText += "__________________________________________________\n";
        cartText+="Total: $" + Math.round(storeManager.getCartTotalPrice(cartId) * 100.0)/100.0;

        if (JOptionPane.showConfirmDialog(FRAME, cartText)
                == JOptionPane.OK_OPTION) {
            // close it down!
            storeManager.checkout(cartId);
            FRAME.setVisible(false);
            FRAME.dispose();
        }
    }
}
