
/**
 * Write a description of class MarkovWordTwo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;

public class MarkovWordTwo implements IMarkovModel
{
    private String[] myText;
    private Random myRandom;
    
    public MarkovWordTwo() {
        myRandom = new Random();
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
    }
    
    public int indexOf(String[] words, String target1, String target2, int start) {
        for (int k = start; k < words.length - 1; k++) {
            if (words[k].equals(target1) && words[k+1].equals(target2)) {
                return k;
            }
        }
        return -1;
    }
    
    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length - 2);  // random word to start with
        String key1 = myText[index];
        String key2 = myText[index+1];
        sb.append(key1);
        sb.append(" ");
        sb.append(key2);
        sb.append(" ");
        for(int k=0; k < numWords - 1; k++) {
            ArrayList<String> follows = getFollows(key1, key2);
            //System.out.println(key + follows);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            int i = 0;
            for (String word : myText) {
                if (word.equals(next)) {
                    break;
                }
                i++;
            }
            sb.append(" ");
            sb.append(myText[i+1]);
            sb.append(" ");
            key1 = next;
            key2 = myText[i+1];
        }
        
        return sb.toString().trim();
    }
    
    private ArrayList<String> getFollows(String key1, String key2) {
        ArrayList<String> follows = new ArrayList<String>();
        int pos = 0;
        while (pos < myText.length - 1) {
            int start = indexOf(myText, key1, key2, pos);
            if (start == -1) {
                break;
            }
            if (start + key1.length() >= myText.length) {
                break;
            }
            String next = myText[start + 2];
            follows.add(next);
            pos = start + 1;
        }
        return follows;
    }

}
