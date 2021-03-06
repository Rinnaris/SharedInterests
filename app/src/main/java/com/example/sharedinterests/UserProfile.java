package com.example.sharedinterests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rinnaris on 2017-03-04.
 */

public class UserProfile {
    //test string 1
    // Bobby~^MOV~Inception~Avengers~^GAME~Civ V~Kingdom hearts~

    //test string 2
    // Bob~^MOV~Inception~Zootopia~^WEB~Youtube~^GAME~Kingdom hearts~

    //there should be 2 matches between the test strings

    /*profile string format is as follows
    //string is delimited by ~ character
    //first word is always user's name
    //categories are preceded by a ^ symbol and are in caps
    //example string:
    //      Aria~^MOV~Inception~Avengers~^GAME~Civ V~Kingdom Hearts~
    //current categories are
        movies - ^MOV, tv - ^TV, websites - ^WEB, music - ^MUS,
        games - ^GAME, sports - ^SPORT, anime - ^ANIM, books - ^BOOK
     */

    //one arraylist per category, all are initialized but can be
    //empty if there are no entries of that type

    //Global Variables
    //**********************************************************

    //Constant String - category names
    //safe to change if needed
    private final String movieString = "MOV";
    private final String tvString = "TV";
    private final String websiteString = "WEB";
    private final String musicString = "MUS";
    private final String gameString = "GAME";
    private final String sportString = "SPORT";
    private final String animeString = "ANIM";
    private final String bookString = "BOOK";
    private final String controlCharacter = "^";
    private final String delimiter = "~";


    //Name
    private String name;

    //list of lists
    //just go with it
    private ArrayList<ArrayList<String>> list;

    //Categories lists
    private ArrayList<String> movies;
    private ArrayList<String> tv;
    private ArrayList<String> websites;
    private ArrayList<String> music;
    private ArrayList<String> games;
    private ArrayList<String> sports;
    private ArrayList<String> anime;
    private ArrayList<String> books;

    //**********************************************************




    //public methods
    //*************************************************************

    //constructer for a blank profile
    public UserProfile(){
        name = null;
        list = null;
    }

    //constructer for profile, give it a formatted string
    public UserProfile(String info){
        //initialize category lists
        initialize();
        //parse string
        readString(info);
    }

    //parse string and place entries in correct arraylists
    public void readString(String info) {
        //new temp arraylist
        ArrayList<String> temp = new ArrayList<>();

        //separates info by delimiter and adds each item separately to items
        List<String> items = Arrays.asList(info.split(delimiter));

        //items is a wrapper not a real list so it is converted into arraylist manually
        for (String s: items) {
            temp.add(s);
        }

        //assign first item in temp to name
        name = temp.get(0);
        name = removeControlCharacters(name);
        temp.remove(0);

        //if profile was just a name stop processing
        if(temp.isEmpty()){
            return;
        }
        ArrayList<String> currentCategory = null;

        for (String s: temp) {


            //if temp has a category control character change current category arraylist
            if(s.contains(controlCharacter)){
                currentCategory = getCategory(s);
            }

            //else add s to current category
            else{
                currentCategory.add(removeControlCharacters(s));
            }
        }
    }

    //converts profile to string
    @Override
    public String toString(){

        String out = name + delimiter; //start string with name+controlcharacter

        //for each category
        for (ArrayList<String> current: list) {

            //if current category is not empty
            if(!current.isEmpty()) {
                //add category name to list
                out = out + getCategory(current) + delimiter;

                //for each string in category
                for (String s : current) {
                    //add current item to string
                    out = out + s + delimiter;
                }
            }
        }

        //return string
        return out;
    }

    //given another user's profile find and return common items
    public ArrayList<String> compare(UserProfile other){
        //set up list for return
        ArrayList<String> returnList = new ArrayList<>();

        //get other profile's master list
        ArrayList<ArrayList<String>> compareObject = other.getList();

        /*//for each category in other list
        for (ArrayList<String> cat: compare) {
            //get corresponding category
            String category = getCategory(cat);
            //for each item in category
            for (String item: cat) {
                //if item was found add it to the list
                if(findItem(item, category)){
                    returnList.add(item);
                }
            }
        }*/

        for(int i = 0; i < 8; i++){
            ArrayList<String> compareCategory = compareObject.get(i);

            if(!compareCategory.isEmpty()) {
                for (String s : compareCategory) {
                    ArrayList<String> thisCategory = this.list.get(i);
                    if(!thisCategory.isEmpty()){
                        for (String y: thisCategory) {
                            if(s.equals(y)){
                                returnList.add(y);
                            }
                        }
                    }
                }
            }
        }
        return returnList;

    }

    //*************************************************************


    //Getters
    //**************************************************************

    public String getName(){
        return name;
    }

    public ArrayList<ArrayList<String>> getList(){
        return list;
    }

    public ArrayList<String> getMovies(){
        return movies;
    }

    public ArrayList<String> getTV(){
        return tv;
    }

    public ArrayList<String> getWebsites(){
        return websites;
    }

    public ArrayList<String> getMusic(){
        return music;
    }

    public ArrayList<String> getGames(){
        return games;
    }

    public ArrayList<String> getSports(){
        return sports;
    }

    public ArrayList<String> getAnime(){
        return anime;
    }

    public ArrayList<String> getBooks(){
        return books;
    }
    //**************************************************************

    //Setters
    //Well not really setters more like adders
    //***********************************************************
    public void setName(String in){
        name = in;
    }

    //adds item to arraylist of category
    public void addItem(String item, String category){
        ArrayList<String> temp = getCategory(category);
        temp.add(item);
    }

    //removes item from given category
    public void removeItem(String item, String category){
        ArrayList<String> temp = getCategory(category);
        temp.remove(item);
    }

    //returns index of item from given category or -1 if not found
    public boolean findItem(String item, String category){
        //get responding category
        ArrayList<String> temp = getCategory(category);

        //there is a method called arraylist.contains but it isn't working so i wrote one myself
        //stupid java and its distinction between == and .equals()
        //if temp exists
        if(temp!= null){
            //for each item in temp
            for (String s: temp) {
                //if the current item in temp = the search item
                if(s.equals(item)){
                    return true;
                }
            }
        }

        //if it hasn't returned true yet return false since item was not found
        return false;
    }
    //*************************************************************


    //Methods for internal use only do not call them from other classes
    //**********************************************************************

    //returns the arraylist corresponsing to the name passed to it
    private ArrayList<String> getCategory(String in) {
        String category = removeControlCharacters(in);

        if(category.equals("MOV")){
            return movies;
        }
        else if(category.equals(tvString)){
            return tv;
        }
        else if(category.equals(websiteString)){
            return websites;
        }
        else if(category.equals(musicString)){
            return music;
        }
        else if(category.equals(gameString)){
            return games;
        }
        else if(category.equals(sportString)){
            return sports;
        }
        else if(category.equals(animeString)){
            return anime;
        }
        else if(category.equals(bookString)){
            return books;
        }
        else {
            return games;
        }
    }

    //returns the string name of the arraylist passed to it
    private String getCategory(ArrayList<String> current) {
        if(current.equals(movies)){
            return controlCharacter + movieString;
        }
        if(current.equals(tv)){
            return controlCharacter + tvString;
        }
        if(current.equals(websites)){
            return controlCharacter + websiteString;
        }
        if(current.equals(music)){
            return controlCharacter + musicString;
        }
        if(current.equals(games)){
            return controlCharacter + gameString;
        }
        if(current.equals(sports)){
            return controlCharacter + sportString;
        }
        if(current.equals(anime)){
            return controlCharacter + animeString;
        }
        if(current.equals(books)){
            return controlCharacter + bookString;
        }
        return "";
    }

    //this doesn't need to be called it's just for initialization purposes and neatness
    private void fillList() {
        list.add(movies);
        list.add(tv);
        list.add(websites);
        list.add(music);
        list.add(games);
        list.add(sports);
        list.add(anime);
        list.add(books);
    }

    private void initialize(){
        //****************************************************
        movies = new ArrayList<>();
        tv = new ArrayList<>();
        websites = new ArrayList<>();
        music = new ArrayList<>();
        games = new ArrayList<>();
        sports = new ArrayList<>();
        anime = new ArrayList<>();
        books = new ArrayList<>();
        //****************************************************

        //initialize list and add categories
        list = new ArrayList<>();
        fillList();

    }

    private String removeControlCharacters(String in) {
        String out = in;
        out = out.replace(delimiter, "");
        out = out.replace(controlCharacter, "");
        return out;
    }

    public boolean isSame(UserProfile other) {
        //conver both this and other profile to strings
        String thisProfile = this.toString();
        String otherProfile = other.toString();
        //return true if the strings are equal
        return thisProfile.equals(otherProfile);
    }

    //**********************************************************************
}
