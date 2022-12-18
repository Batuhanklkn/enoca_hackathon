package org.ClientManager;

public class CMMain {

    public CMMain() {
    }

    public static void main(String[] args) {
        //main frame
        CMFront cmsv = new CMFront();
        //insert section of the main frame
        InsertClientView icv = new InsertClientView();
        //database activity of the app
        CMModel cmsm = new CMModel();
        //control and listeners
        CMController theController = new CMController(cmsv, icv, cmsm);
        theController.run();
    }
}
