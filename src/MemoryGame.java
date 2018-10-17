import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryGame implements ActionListener {
    private ArrayList<JToggleButton> buttonList;
    private JLabel timeLabel;
    private Timer delay;
    private Timer totalTime;
    private int hh;
    private int mm;
    private int ss;
    private int totalCardsMatched =1;
    private int currentlyClicked = 1;
    private int lastCardIndex = -1;
    private int currentCardIndex = -2;

    public MemoryGame(String debug){
        JFrame jFrame = new JFrame("Memory Game");
        jFrame.setSize(715,540);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(new BorderLayout());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon img = new ImageIcon("MemoryGame.png");
        jFrame.setIconImage(img.getImage());

        ActionListener delayAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delayTime();
            }
        };

        delay = new Timer(1500, delayAL);

        timeLabel = new JLabel("00:00:00");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Action Listener for totalTime
        ActionListener totalTimeAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        };

        // TO-DO
        // MAKE IT START AFTER USER CLICKS A CARD
        totalTime = new Timer(1000, totalTimeAL);

        jFrame.add(timeLabel, BorderLayout.NORTH);

        JPanel jPanel = new JPanel(new GridLayout(3, 4));

        buttonList = new ArrayList<>();
        for(int i = 1; i <= 12; i++){
            JToggleButton button = new JToggleButton(Integer.toString(i));
            // button.setActionCommand(Integer.toString(i));

            try {
                ImageIcon defaultImage = new ImageIcon(ImageIO.read(getClass().getResource("MemoryGame.png")));
                if(i>6){
                    ImageIcon image = new ImageIcon(ImageIO.read(getClass().getResource(Integer.toString(i-6) + ".png")));
                    button.setIcon(image);
                    button.setSelectedIcon(defaultImage);
                    //   button.setDisabledIcon(defaultImage);
                    button.setDisabledSelectedIcon(defaultImage);

                }else{
                    ImageIcon image = new ImageIcon(ImageIO.read(getClass().getResource(Integer.toString(i) + ".png")));
                    button.setIcon(image);
                    button.setSelectedIcon(defaultImage);
                    // button.setDisabledIcon(defaultImage);
                    button.setDisabledSelectedIcon(defaultImage);


                }
            }catch (IOException ioe){
                System.out.println(ioe.getStackTrace());
            }

            button.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    // Starts timer after first click
                    if(!totalTime.isRunning() && totalCardsMatched < 12){
                        totalTime.start();
                    }

                    if(currentlyClicked == 1){
                        // get index from buttonList
                        lastCardIndex = buttonList.indexOf(e.getSource());
                        // stop that button from being clicked twice in a row
                        buttonList.get(lastCardIndex).setEnabled(false);

                        currentlyClicked++;
                    }
                    else if(currentlyClicked == 2){
                        currentCardIndex = buttonList.indexOf(e.getSource());


                        if(Integer.parseInt(buttonList.get(currentCardIndex).getText())%6 ==
                                Integer.parseInt(buttonList.get(lastCardIndex).getText())%6){


                            buttonList.get(currentCardIndex).setText("match");
                            buttonList.get(lastCardIndex).setText("match");

                            buttonList.get(currentCardIndex).setEnabled(false);
                            buttonList.get(lastCardIndex).setEnabled(false);

                            totalCardsMatched+=2;
                            if(totalCardsMatched >= 11){
                                totalTime.stop();
                            }
                        }else{
                            delay.setRepeats(true);
                            disableButtons();
                            delay.start();

                        }
                        currentlyClicked = 1;
                    }else{
                        currentlyClicked++;
                    }

                }
            });

            buttonList.add(button);
        }

        Collections.shuffle(buttonList);
        for(int i = 0; i < buttonList.size(); i++){
            jPanel.add(buttonList.get(i));
        }

        jFrame.add(jPanel);

        jFrame.setVisible(true);
    }

    public MemoryGame(){
        JFrame jFrame = new JFrame("Memory Game");
        jFrame.setSize(715,540);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(new BorderLayout());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon img = new ImageIcon("MemoryGame.png");
        jFrame.setIconImage(img.getImage());

        ActionListener delayAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delayTime();
            }
        };

        delay = new Timer(1500, delayAL);

        timeLabel = new JLabel("00:00:00");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Action Listener for totalTime
        ActionListener totalTimeAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        };

        // TO-DO
        // MAKE IT START AFTER USER CLICKS A CARD
        totalTime = new Timer(1000, totalTimeAL);

        jFrame.add(timeLabel, BorderLayout.NORTH);

        JPanel jPanel = new JPanel(new GridLayout(3, 4));

        buttonList = new ArrayList<>();
        for(int i = 1; i <= 12; i++){
            JToggleButton button = new JToggleButton(Integer.toString(i));
           // button.setActionCommand(Integer.toString(i));

            try {
                ImageIcon defaultImage = new ImageIcon(ImageIO.read(getClass().getResource("MemoryGame.png")));
                button.setIcon(defaultImage);
                if(i>6){
                    ImageIcon image = new ImageIcon(ImageIO.read(getClass().getResource(Integer.toString(i-6) + ".png")));
                    button.setSelectedIcon(image);
                 //   button.setDisabledIcon(defaultImage);
                    button.setDisabledSelectedIcon(image);

                }else{
                    ImageIcon image = new ImageIcon(ImageIO.read(getClass().getResource(Integer.toString(i) + ".png")));
                    button.setSelectedIcon(image);
                   // button.setDisabledIcon(defaultImage);
                    button.setDisabledSelectedIcon(image);


                }
            }catch (IOException ioe){
                System.out.println(ioe.getStackTrace());
            }

            button.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    // Starts timer after first click
                    if(!totalTime.isRunning() && totalCardsMatched < 12){
                        totalTime.start();
                    }

                    if(currentlyClicked == 1){
                        // get index from buttonList
                        lastCardIndex = buttonList.indexOf(e.getSource());
                        // stop that button from being clicked twice in a row
                        buttonList.get(lastCardIndex).setEnabled(false);

                        currentlyClicked++;
                    }
                    else if(currentlyClicked == 2){
                        currentCardIndex = buttonList.indexOf(e.getSource());


                        if(Integer.parseInt(buttonList.get(currentCardIndex).getText())%6 ==
                                Integer.parseInt(buttonList.get(lastCardIndex).getText())%6){


                            buttonList.get(currentCardIndex).setText("match");
                            buttonList.get(lastCardIndex).setText("match");

                            buttonList.get(currentCardIndex).setEnabled(false);
                            buttonList.get(lastCardIndex).setEnabled(false);

                            totalCardsMatched+=2;
                            if(totalCardsMatched >= 11){
                                totalTime.stop();
                            }
                        }else{
                            delay.setRepeats(true);
                            disableButtons();
                            delay.start();

                        }
                        currentlyClicked = 1;
                    }else{
                        currentlyClicked++;
                    }

                }
            });

            buttonList.add(button);
        }

        Collections.shuffle(buttonList);
        for(int i = 0; i < buttonList.size(); i++){
            jPanel.add(buttonList.get(i));
        }

        jFrame.add(jPanel);

        jFrame.setVisible(true);
    }

    public void disableButtons(){
        for(int i =0; i < buttonList.size(); i++){
            buttonList.get(i).setEnabled(false);
        }
    }

    public void enableButtons(){
        for(int i =0; i < buttonList.size(); i++){
            if(!buttonList.get(i).getText().equals("match"))
               buttonList.get(i).setEnabled(true);
        }
    }

    public void delayTime(){
        currentlyClicked--;
        buttonList.get(currentCardIndex).setEnabled(true);
        buttonList.get(lastCardIndex).setEnabled(true);
        currentlyClicked--;
        buttonList.get(currentCardIndex).setSelected(false);
        buttonList.get(lastCardIndex).setSelected(false);
        enableButtons();

        currentCardIndex = -2;
        lastCardIndex = -1;

        delay.stop();

    }

    // Keeps track of time once user starts the game.
    // Stops when totalCardsMatched == 16;
    public void updateTime(){
        String seconds;
        String minutes;
        String hours;

        if(ss==60){
            ss = 0;
            mm++;
        } else{
            ss++;
        }
        if(mm==60){
            mm = 0;
            hh++;
        }
        if(ss < 10){
            seconds = "0" + Integer.toString(ss);
        }else{
            seconds = Integer.toString(ss);
        }
        if(mm < 10){
            minutes = "0" + Integer.toString(mm);
        }else{
            minutes = Integer.toString(mm);
        }
        if(hh < 10){
            hours = "0" + Integer.toString(hh);
        }else{
            hours = Integer.toString(hh);
        }
        timeLabel.setText(hours + ":" + minutes + ":" + seconds);

    }

    public void actionPerformed(ActionEvent ae){

    }

    public static void main(String[] args){
        //SwingUtilities.invokeLater(() -> new MemoryGame());

        if(args.length > 0){
            try{
                if(args[0].equals("debug")){
                    SwingUtilities.invokeLater(() -> new MemoryGame("debug"));
                }
            }catch (NumberFormatException e){
                System.out.println(e.getStackTrace());
            }
        }else{
            SwingUtilities.invokeLater(() -> new MemoryGame());
        }
    }
}
