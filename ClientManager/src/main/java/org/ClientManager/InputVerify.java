package org.ClientManager;

import java.util.regex.Pattern;

public class InputVerify {
    //a class to verify inputs correctness
    private CMFront cms;

    public InputVerify(CMFront cms) {
        this.cms = cms;
    }

    //actually this part supposed to be better, need to check more probablities of inputs.
    //look here after the hackathon to improve
    public boolean isInvalidMailPass(String mailt, String passwordt) {
        //parsing mail to get a clear data managment is importent, so just parse the mail at "@" character
        if (mailt.length() != 0 && mailt.length() <= 60 && passwordt.length() != 0 && passwordt.length() <= 60) {
            return false;
        } else {
            //in this part of the code, we can check more than length for mail and pass
            //actually we should check seperatly but im tired, so its all on you mate :)
            this.cms.errorMessage(this.cms, "Mail or pass incorrect!");
            return true;
        }
    }

    public boolean isInvalidAddress(String address) {
        //In this part actually we supposed to assign a variable for the max sql varchar length for data to change it later.
        if (address.length() != 0 && address.length() <= 60) {
            return false;
        } else {
            this.cms.errorMessage(this.cms, "Address cannot be empty and must be less than 60 characters");
            return true;
        }
    }

    //this "t's" at the ends are confusing may be we can change their name for later, but i wont do that lol
    public boolean isInvalidDistrict(String districtt) {

        if (districtt.length() <= 60 && districtt.length() !=0) {
            return false;
        } else {
            this.cms.errorMessage(this.cms, "district cannot be longer than 60, and cannot be empty");
            return true;
        }
    }

    public boolean isInvalidCity(String city) {
        //this parts always can be better that this version but im nearly dead because of the cafein so, i want do that :/
        if (city.length() <= 60 && city.length() != 0 ) {
            return false;
        } else {
            this.cms.errorMessage(this.cms, "city cannot be longer than 60, and cannot be empty");
            return true;
        }
    }

    public boolean isInvalidID(String id) {
        Pattern IDPattern = Pattern.compile("^[0-9]+$");
        if (id.length() <= 4 && IDPattern.matcher(id).matches()) {
            return false;
        } else {
            this.cms.errorMessage(this.cms, "ID cannot be empty, and must contain only digits (up to 4)");
            return true;
        }
    }
}
