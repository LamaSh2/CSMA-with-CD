import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CSMA_CD_Simulation extends JFrame {
    private JTextArea logTextArea;
    private JButton sendButton;
    private NodePanel nodePanel;

    private static final int NUM_NODES = 5;
    private static final int MAX_BACKOFF_TIME = 10;

    private enum NodeState { IDLE, TRANSMITTING, BACKOFF, COLLISION }

    private class Node {
        private int id;
        private NodeState state;
        private int backoffTime;

        public Node(int id) {
            this.id = id;
            this.state = NodeState.IDLE;
            this.backoffTime = 0;
        }

        public void startTransmission() {
            state = NodeState.TRANSMITTING;
            logTextArea.append("Node " + id + " starts transmission.\n");
            nodePanel.repaint();
        }

        public void startBackoff() {
            state = NodeState.BACKOFF;
            backoffTime = (int) (Math.random() * MAX_BACKOFF_TIME);
            logTextArea.append("Node " + id + " starts backoff with time " + backoffTime + ".\n");
            nodePanel.repaint();
        }

        public void handleCollision() {
            state = NodeState.COLLISION;
            backoffTime = (int) (Math.random() * MAX_BACKOFF_TIME);
            logTextArea.append("Collision detected for node " + id + ". Backing off with time " + backoffTime + ".\n");
            nodePanel.repaint();
        }

        public void handleSuccessfulTransmission() {
            state = NodeState.IDLE;
            logTextArea.append("Node " + id + " transmitted successfully.\n");
            nodePanel.repaint();
        }

        public void update() {
            switch (state) {
                case IDLE:
                    break;
                case TRANSMITTING:
                    if (Math.random() < 0.2) { // Simulate collision
                        handleCollision();
                    } else {
                        handleSuccessfulTransmission();
                    }
                    break;
                case BACKOFF:
                    if (backoffTime == 0) {
                        startTransmission();
                    } else {
                        backoffTime--;
                    }
                    break;
                case COLLISION:
                    if (backoffTime == 0) {
                        startBackoff();
                    } else {
                        backoffTime--;
                    }
                    break;
            }
        }
    }

    private class NodePanel extends JPanel {
        private static final int BOX_SIZE = 60;
        private static final int BOX_MARGIN = 10;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int totalWidth = (NUM_NODES * BOX_SIZE) + ((NUM_NODES + 1) * BOX_MARGIN);
            int startX = (panelWidth - totalWidth) / 2;

            for (int i = 0; i < NUM_NODES; i++) {
                int x = startX + (i * (BOX_SIZE + BOX_MARGIN));
                int y = (panelHeight - BOX_SIZE) / 2;

                Node node = nodes.get(i);
                g.setColor(getNodeColor(node.state));
                g.fillRect(x, y, BOX_SIZE, BOX_SIZE);

                g.setColor(Color.BLACK);
                g.drawString(Integer.toString(node.id), x + (BOX_SIZE / 2) - 5, y + (BOX_SIZE / 2) + 5);
            }
        }

        private Color getNodeColor(NodeState state) {
            switch (state) {
                case IDLE:
                    return Color.GREEN;
                case TRANSMITTING:
                    return Color.BLUE;
                case BACKOFF:
                    return Color.ORANGE;
                case COLLISION:
                    return Color.RED;
                default:
                    return Color.GRAY;
            }
        }
    }

    private List<Node> nodes;

    public CSMA_CD_Simulation() {
        setTitle("CSMA/CD Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        add(scrollPane, BorderLayout.CENTER);

        sendButton = new JButton("Send Packet");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendPacket();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sendButton);
        add(buttonPanel, BorderLayout.SOUTH);

        nodePanel = new NodePanel();
        add(nodePanel, BorderLayout.NORTH);

        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        initializeSimulation();

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSimulation();
            }
        });
        timer.start();

    }

    private void initializeSimulation() {
        nodes = new ArrayList<>();
        for (int i = 0; i < NUM_NODES; i++) {
            nodes.add(new Node(i));
        }
    }

    private void sendPacket() {
        Node transmittingNode = getRandomIdleNode();
        if (transmittingNode != null) {
            transmittingNode.startTransmission();
        }
    }

    private Node getRandomIdleNode() {
        List<Node> idleNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node.state == NodeState.IDLE) {
                idleNodes.add(node);
            }
        }

        if (!idleNodes.isEmpty()) {
            int randomIndex = (int) (Math.random() * idleNodes.size());
            return idleNodes.get(randomIndex);
        }

        return null;
    }

    private void updateSimulation() {
        for (Node node : nodes) {
            node.update();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CSMA_CD_Simulation();
            }
        });
    }
}