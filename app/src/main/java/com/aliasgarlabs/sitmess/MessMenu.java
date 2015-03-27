package com.aliasgarlabs.sitmess;

/**
 * Created by Aliasgar Murtaza on 20-Mar-15.
 */

    import com.parse.ParseClassName;
    import com.parse.ParseObject;

    @ParseClassName("Menu")
    public class MessMenu extends ParseObject{

        public MessMenu(){

        }

        public String getBreakfast(){
            return getString("breakfast");
        }


        public void setBreakfast(String breakfast){
            put("breakfast", breakfast);

        }


        public String getLunch(){
            return getString("lunch");
        }

        public void setLunch(String lunch){
            put("lunch", lunch);
        }

        public String getDinner(){
            return getString("dinner");
        }

        public void setDinner(String dinner){
            put("dinner", dinner);
        }

        public String getSnack(){
            return getString("snack");
        }

        public void setSnack(String snack){
            put("snack", snack);
        }

        public String getMenuDate(){
            return getString("date");
        }

        public void setMenuDate(String date){
            put("date", date);
        }

        public int getLikes(){
            return getInt("likes");
        }

        public void setLikes(int likes){
            put("likes", likes);
        }

        public int getAttendance(){
            return getInt("attend");
        }

        public void setAttendance(int attend){
            put("attend", attend);
        }

}
