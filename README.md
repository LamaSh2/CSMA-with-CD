# CSMA/CD Simulation
![CSMA_CD_PIC](https://github.com/LamaSh2/CSMA-with-CD/assets/79080627/dcaf9f15-ac77-463e-a563-f6106daab475)

This is a Java program that simulates the Carrier Sense Multiple Access with Collision Detection (CSMA/CD) protocol. CSMA/CD is a media access control (MAC) protocol used in Ethernet networks to regulate access to the network medium and handle collisions when multiple nodes attempt to transmit data simultaneously.


## Usage

Once the simulation is running, you will see a graphical user interface (GUI) window with a log area and a "Send Packet" button. The window also displays a visual representation of the nodes' states.

- The log area shows the events occurring during the simulation, such as node transmissions, collisions, and backoff times.
- Clicking the "Send Packet" button initiates a packet transmission. The program selects a random idle node to start transmitting.
- The node states are represented by colored rectangles:
  - Green: Idle state
  - Blue: Transmitting state
  - Orange: Backoff state
  - Red: Collision state

The simulation automatically updates every second, simulating the passage of time and the behavior of the CSMA/CD protocol.

## Customization

You can customize the simulation by modifying the following constants in the source code:

- `NUM_NODES`: The number of nodes participating in the simulation.
- `MAX_BACKOFF_TIME`: The maximum backoff time for nodes in the simulation.

Feel free to experiment with different values to observe the behavior of the CSMA/CD protocol under various conditions.
