//Bardia Parmoun 101143006
//Guy Morgenshtern 101151430

package store;

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
    private final JFrame frame;

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

    /**
     * The default constructor for storeview which creates an instance of a user
     * @param storeManager the manager of the store
     * @param cartId the id of the  cart
     */
    public StoreView(StoreManager storeManager, int cartId) {
        this.storeManager = storeManager;
        this.cartId = cartId;
        this.frame = new JFrame();
        this.productPanels = new ArrayList<>();
        this.productInfoLabels = new HashMap<>();

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
                    JOptionPane.showMessageDialog(frame, "Illegal amount!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    productInfoLabels.get(product).setText("Price: $" + product.getPrice() + " Stock:" +
                            storeManager.checkStock(product));
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
                    JOptionPane.showMessageDialog(frame, "Illegal amount!");
                }
                else{
                    productInfoLabels.get(product).setText("Price: $" + product.getPrice() + " Stock:" +
                            storeManager.checkStock(product));
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
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to empty your cart?")
                        == JOptionPane.OK_OPTION) {
                    // close it down!
                    storeManager.emptyCart(cartId);
                    for (Product p: productInfoLabels.keySet()) {
                        productInfoLabels.get(p).setText("Price: $" + p.getPrice() + " Stock:" +
                                storeManager.checkStock(p));
                    }
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
        button.setSize(50,30);

        // add the action listener
        button.addActionListener(new ActionListener() {

            // this method will be called when we click the button
            @Override
            public void actionPerformed(ActionEvent ae) {
                    if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to checkout?")
                                == JOptionPane.OK_OPTION) {
                            // close it down!
                            displayCart();
                            storeManager.checkout(cartId);
                            frame.setVisible(false);
                            frame.dispose();
                    }
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
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    // close it down!
                    storeManager.emptyCart(cartId);
                    frame.setVisible(false);
                    frame.dispose();
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
        frame.setTitle("Guy and Bardia's Pie and Media!");
        frame.setResizable(false);

        JPanel mainPanel =  new JPanel(new GridBagLayout());
        JPanel headerPanel = new JPanel();
        JPanel leftSpacePanel = new JPanel();
        JPanel middleSpacePanel = new JPanel();
        JPanel rightSpacePanel = new JPanel();

        JPanel productPanel = new JPanel(new GridLayout(3,2));
        JPanel menuPanel = new JPanel(new GridLayout(3,1));
        JPanel footerPanel = new JPanel();

        // create your JLabels here
        Font fontTitle = new Font("Comic Sans Ms", Font.BOLD + Font.ITALIC, 14);
        JLabel headerLabel = new JLabel("Welcome to Guy and Bardia's Pie and Media! (ID: " + cartId + ")");
        headerLabel.setFont(fontTitle);

        // set the preferred sizes and colours
        headerPanel.setPreferredSize(new Dimension(500, 50));
        productPanel.setPreferredSize(new Dimension(500, 700));
        menuPanel.setPreferredSize(new Dimension(150, 100));
        leftSpacePanel.setPreferredSize(new Dimension(50, 100));
        middleSpacePanel.setPreferredSize(new Dimension(50, 100));
        rightSpacePanel.setPreferredSize(new Dimension(50, 100));
        footerPanel.setPreferredSize(new Dimension(500, 50));


        // adding JLabel to the header panel and footer panel
        headerPanel.add(headerLabel,BorderLayout.CENTER);
        footerPanel.add(getEmptyCartButton());

        displayItems();


        for (JPanel p: productPanels){
            productPanel.add(p);
        }

        try {
            BufferedImage img = ImageIO.read(new URL(
                    "https://user-images.githubusercontent.com/59774562/113081179-32ee2a80-91a6-11eb-8dfb-0d6d2b17ec4a.png"));
            Image dimg = img.getScaledInstance(50,50, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(dimg);
            menuPanel.add(getViewCartButton(icon), BorderLayout.NORTH);
            menuPanel.add(getCheckoutButton(), BorderLayout.CENTER);
            menuPanel.add(getQuitButton(), BorderLayout.SOUTH);
        }

        catch (IOException e){
            JOptionPane.showMessageDialog(frame, "Could not load the image", "Error", JOptionPane.ERROR_MESSAGE);
        }


        // adding all the panels to the main panel
        GridBagConstraints gbagConstraintsHeader = new GridBagConstraints();
        gbagConstraintsHeader.gridx = 1;
        gbagConstraintsHeader.gridy = 0;
        mainPanel.add(headerPanel, gbagConstraintsHeader);

        GridBagConstraints gbagConstraintsLeftSpace = new GridBagConstraints();
        gbagConstraintsLeftSpace.gridx = 0;
        gbagConstraintsLeftSpace.gridy = 1;
        mainPanel.add(leftSpacePanel, gbagConstraintsLeftSpace);

        GridBagConstraints gbagConstraintsProduct = new GridBagConstraints();
        gbagConstraintsProduct.gridx = 1;
        gbagConstraintsProduct.gridy = 1;
        mainPanel.add(productPanel, gbagConstraintsProduct);

        GridBagConstraints gbagConstraintsMiddleSpace = new GridBagConstraints();
        gbagConstraintsMiddleSpace.gridx = 2;
        gbagConstraintsMiddleSpace.gridy = 1;
        mainPanel.add(middleSpacePanel, gbagConstraintsMiddleSpace);

        GridBagConstraints gbagConstraintsMenu = new GridBagConstraints();
        gbagConstraintsMenu.gridx = 3;
        gbagConstraintsMenu.gridy = 1;
        mainPanel.add(menuPanel, gbagConstraintsMenu);

        GridBagConstraints gbagConstraintsRightSpace = new GridBagConstraints();
        gbagConstraintsRightSpace.gridx = 4;
        gbagConstraintsRightSpace.gridy = 1;
        mainPanel.add(rightSpacePanel, gbagConstraintsRightSpace);

        GridBagConstraints gbagConstraintsFooter = new GridBagConstraints();
        gbagConstraintsFooter.gridx = 1;
        gbagConstraintsFooter.gridy = 2;
        mainPanel.add(footerPanel, gbagConstraintsFooter);

        // add the window listener
        // we no longer want the frame to close immediately when we press "x"
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    // close it down!
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });

        // the frame is not visible until we set it to be so
        frame.setVisible(true);


        // pack
        frame.add(mainPanel);
        frame.pack();
        return true;
    }

    /**
     * Displays the removefromcart interface where the user is prompted to choose a product and a proper amount which
     * is then removed from the cart
     */
    private boolean removeFromCartUI(String product, int amount){
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

        int width = 200;
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
                BufferedImage img = ImageIO.read(new URL(
                        storeManager.getAvailableProducts().get(i).getImageURL()));

                //resizing the image
                Image dimg = img.getScaledInstance(width,height, Image.SCALE_SMOOTH);

                //adding the image to the label
                ImageIcon icon = new ImageIcon(dimg);
                productLabel.setIcon(icon);


                //adding the label to the panel
                JPanel productPanel = new JPanel();
                productPanel.setPreferredSize(new Dimension(width, height+100));

                JLabel productInfo = new JLabel("Price: $" + price + " Stock:" + stock);

                productInfoLabels.put(p,productInfo);

                productPanel.add(productInfo,BorderLayout.NORTH);

                productPanel.add(productLabel,BorderLayout.CENTER);

                productPanel.add(getRemoveFromCartButton(p), BorderLayout.SOUTH);

                productPanel.add(getAddToCartButton(p), BorderLayout.SOUTH);

                //adding the border
                TitledBorder title = BorderFactory.createTitledBorder(name);
                productPanel.setBorder(title);

                productPanels.add(productPanel);
            }
            catch(IOException e){
                JOptionPane.showMessageDialog(frame, "Could not load the image" , "Error", JOptionPane.ERROR_MESSAGE);
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
        cartText+="Cart total: " + Math.round(storeManager.getCartTotalPrice(cartId) * 100.0)/100.0;

        JOptionPane.showMessageDialog(frame, cartText, "Your cart!", JOptionPane.INFORMATION_MESSAGE);
    }
}
