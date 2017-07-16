package com.swiggy.swag.swagapp.common;

/**
 * Created by 127.0.0.1.ma on 15/07/17.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.StringTokenizer;


public class StopWordRemoval {
    public static final String[] stop_word_list = {"secondly", "all", "consider", "whoever", "four", "edu", "go", "mill", "causes", "seemed", "rd", "certainly",
            "system", "when's", "show", "vs", "to", "asking", "th", "under", "sorry", "a's", "sent", "@", "far", "theyve", "every", "yourselves", "`",
            "we'll", "went", "did", "forth", "they've", "try", "he'll", "havent", "it'll", "i'll", "says", "you'd", "ten", "yourself", "whens", "likely",
            "further", "even", "what", "appear", "heres", "+", "brief", "goes", "sup", "new", ";", "ever", "thin", "full", "c'mon", "whose", "youd",
            "respectively", "sincere", "never", "here", "let", "others", "hadn't", "along", "aren't", "fifteen", "didnt", "allows", "amount", "i'd", "howbeit",
            "he'd", "usually", "whereupon", "i'm", "changes", "thats", "hither", "via", "followed", "merely", "while", "put", "viz", "itse", "everybody", "use",
            "from", "would", "&", "contains", "two", "next", "few", "call", "therefore", "taken", "themselves", "thru", "tell", "more", "``", "knows", "becomes",
            "hereby", "herein", "ain't", "particular", "known", "must", "me", "none", "wouldnt", "this", "oh", "anywhere", "nine", "can", "theirs", "following",
            "my", "example", "indicated", "give", "didn't", "indicates", "something", "want", "!", "needs", "rather", "meanwhile", "how", "instead", "??",
            "shouldnt", "okay", "tried", "may", "after", "different", "hereupon", "such", "?!", "a", "third", "whenever", "maybe", "appreciate", ")", "ones", "so",
            "specifying", "allow", "keeps", "that's", "six", "help", "don't", "indeed", "over", "werent", "mainly", "soon", "course", "isnt", "through", "looks",
            "fify", "still", "its", "before", "thank", "he's", "selves", "inward", ",", "actually", "better", "whether", "willing", "thanx", "ours", "might", "<",
            "haven't", "then", "non", "someone", "somebody", "thereby", "you've", "they", "not", "now", "nor", "wont", "several", "hereafter", "always", "reasonably",
            "whither", "she's", "doesnt", "each", "found", "entirely", "mustn't", "isn't", "mean", "everyone", "doing", "eg", "ex", "our", "beyond", "couldnt", "out"
            , "them", "'", "furthermore", "since", "forty", "looking", "re", "seriously", "shouldn't", "they'll", "got", "cause", "thereupon", "you're", "given",
            "quite", "que", "besides", "ask", "anyhow", "..", "could", "tries", "keep", "ltd", "hence", "onto", "think", "first", "already", "seeming", "*", "thereafter",
            "one", "done", "another", "thick", "awfully", "doesn't", "little", "their", "twenty", "top", "accordingly", "least", "name", "anyone", "indicate",
            "too", "hundred", "gives", "mostly", "behind", "nobody", "took", "immediate", "regards", "somewhat", "hadnt", "kept", "believe", "herself", "than", "here's",
            "unfortunately", "gotten", "second", "i", "were", "toward", "are", "and", "wasnt", "beforehand", "mine", "say", "unlikely", "have", "need", "seen", "seem",
            "saw", "any", "relatively", "zero", "thoroughly", "latter", "that", "downwards", "aside", "thorough", "also", "take", "which", "exactly", "unless", "shall",
            "who", "where's", "most", "eight", "but", "nothing", "]", "why", "sub", "especially", "noone", "later", "amoungst", "yours", "you'll", "definitely", "she'd",
            "normally", "came", "saying", "particularly", "whys", "anyway", "find", "fifth", "(", "outside", "should", "only", "going", "specify", "herse", "do", "his",
            "above", "get", "between", "overall", "truly", "they'd", "cannot", "nearly", "despite", "during", "him", "regarding", "qv", "cry", "twice", "she", "contain",
            "won't", "where", "thanks", "ignored", "eleven", "up", "namely", "computer", "anyways", "best", "wonder", "#", "said", "away", "currently", "please", "enough",
            "there's", "various", "hopefully", "probably", "neither", "youll", "across", "available", "we", "useful", "however", "come", "both", "last", "many", "wouldn't",
            "thence", "according", "against", "etc", "became", "com", "can't", "otherwise", "among", "presumably", "co", "afterwards", "seems", "whatever", "alone",
            "couldn't", "moreover", "throughout", "[", "considering", "sensible", "described", "it's", "three", "been", ".", "whom", "much", "dont", "wherein", "interest",
            "hardly", "it'd", ">", "wants", "corresponding", "fire", "latterly", "concerning", "-", "else", "mustnt", "hers", "former", "those", "^", "myself", "novel",
            "look", "these", "bill", "value", "will", "near", "theres", "seven", "whereafter", "...", "almost", "wherever", "is", "thus", "it", "cant", "itself", "im",
            "in", "return", "ie", "if", "containing", "perhaps", "insofar", "same", "clearly", "beside", "when", "gets", "weren't", "used", "see", "somewhere", "upon",
            "uses", "'ll", "{", "wheres", "hows", "off", "whereby", "nevertheless", "whole", "youre", "well", "anybody", "obviously", "de", "without", "comes", "very", "the", "con", "self", "lest", "she'll", "just", "less", "being", "able", "liked", "front", "greetings", "regardless", "yes", "yet", "unto", "move", "$", "we've", "had", "except", "lets", "has", "ought", "t's", "arent", "around", "who's", "possible", "five", "know", "using", "part", "apart", "necessary", "like", "follows", "theyre", "either", "become", "towards", "therein", "why's", "because", "old", "often", "some", "somehow", "sure", "shes", "specified", "ourselves", "shan't", "happens", "for", "bottom", "though", "per", "everything", "does", "provides", "tends", "?", "be", "nowhere", "although", "sixty", "by", "on", "about", "ok", "anything", "getting", "of", "side", "whence", "plus", "youve", "consequently", "or", "!!", "seeing", "own", "whats", "formerly", "twelve", "into", "within", "due", "down", "appropriate", "right", "c's", "whos", "your", "!?", "fill", "how's", "her", "hes", "there",
            "myself", "inasmuch", "inner", "way", ":", "was", "himself", "elsewhere", "i've", "becoming", "amongst", "back", "hi", "shant", "trying", "with", "he", "they're", "made", "wasn't", "wish", "hasn't", "us", "until", "placed", "below", "un", "empty", "himself", "we'd", "gone", "sometimes", "associated", "certain", "describe", "am", "an", "''", "as", "sometime", "at", "et", "inc", "again", "hasnt", "%", "no", "whereas", "nd", "detail", "lately", "other", "you", "really", "what's", "'s", "welcome", "let's", "ours ", "serious", "'m", "}", "together", "having", "we're", "everywhere", "theyll", "hello", "once"};
    public static final HashSet<String> stop_word_hs = new HashSet<String>(Arrays.asList(stop_word_list));

    public static HashMap <String,Double> removeStopWords(String str) {
        str=str.replaceAll("[^a-zA-Z0-9\\s]", " ").replaceAll("\\s{2,}", " ").toLowerCase();
        StringTokenizer st = new StringTokenizer(str, " ");
        ArrayList<String> temp = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            String t = st.nextToken();

            if (t.length() > 3)
                temp.add(t.trim());
        }
        HashMap <String,Double> curated_words = new HashMap <String,Double> ();
        ArrayList <String> monograms = new ArrayList <String> ();

        for (String str2 : temp) {
            if (!stop_word_hs.contains(str2.toLowerCase())) {
                if(curated_words.containsKey(str2)) {
                    curated_words.put(str2, curated_words.get(str2) + 1.0);
                }
                else {
                    curated_words.put(str2, 1.0);
                    monograms.add(str2);
                }
            }

        }

        for(int i=0;i<monograms.size()-1;i++){
            String t = monograms.get(i) + " " + monograms.get(i+1);
            if(curated_words.containsKey(t))
                curated_words.put(t,curated_words.get(t)+1.2);
            else
                curated_words.put(t,1.0);

        }
        for(int i=0;i<monograms.size()-2;i++){
            String t = monograms.get(i) + " " + monograms.get(i+1)+ " " + monograms.get(i+2);
            if(curated_words.containsKey(t))
                curated_words.put(t,curated_words.get(t)+1.4);
            else
                curated_words.put(t,1.0);
        }

        return curated_words;
    }

    public static void main(String[] args) {
        String str = "Gizmodo UK SEARCH Follow Gizmodo UK Twitter Like Gizmodo UK Facebook Recommend Gizmodo UK Google+  WATCH THIS #comments u6lywxlfdjbb2dwon1ki Musk’s Hyperloop Has Nothing on This Quantum Levitation Race Track By  Andrew Liszewski  on  14 Jul 2017  at  1:00AM He might have billions of dollars, a company that promises to  revolutionise space travel , and an  upgraded full head of hair , but  Elon Musk’s Hyperloop  will never be as cool as this superconducting quantum levitation Möbius strip race track created by students at Ithaca College’s Low Temperature Physics Lab. Möbius strips, as we all learned in science lessons, turn both sides of a strip of paper (or in this case, a flat track) into one continuous looped surface. The students at Ithaca College added three twists to their track, creating a 3π Möbius strip which they then covered with 1,000 magnets so that a liquid nitrogen-cooled superconductor was able to levitate both above and beneath it as it raced in circles.   Superconducting Quantum Levitation on a 3π Möbius Strip    If this is what the Hyperloop would look like one day, potential investors would be lined up around the corner. [ YouTube  via  Geekologie ]   More Watch this Posts: By Andrew Liszewski on 12 Jul 2017 at 9:00PM Incredible Slo-Mo Footage Reveals Hummingbirds are Nature's Fighter Jets By Ryan F. Mandelbaum on 12 Jul 2017 at 8:30PM This New Interactive Documentary on Viruses is Spellbinding By Andrew Liszewski on 12 Jul 2017 at 8:00PM This Fluid Artist's Messy Painting Style is So Wonderfully Soothing to Watch By Charles Pulliam-Moore on 12 Jul 2017 at 6:30PM The Only Way These Rick and Morty Shorts Could Be Better is If They Were Canonical Tags: watch this magnets levitation invideo elon musk hyperloop f7dccc861de425e983b9d5417282cc7d Andrew Liszewski       by Taboola  Sponsored Links  You May Like Say Goodbye To Hair-Fall Forever. Consult at Just Rs. 250 Say Goodbye To Hair-Fall Forever. Consult at Just Rs. 250 Dr Batra's Positive Homeopathy Say Goodbye To Hair-Fall Forever. Consult at Just Rs. 250 Reimagining The Future Through The Power Of Industrial Internet Reimagining The Future Through The Power Of Industrial Internet Bloomberg Quint Reimagining The Future Through The Power Of Industrial Internet Join Online certification course in Supply Chain from SPJIMR Join Online certification course in Supply Chain from SPJIMR SPJIMR Mumbai Join Online certification course in Supply Chain from SPJIMR Become Digital Marketing Certified from IIM Kashipur Become Digital Marketing Certified from IIM Kashipur TalentEdge Become Digital Marketing Certified from IIM Kashipur 0 SHARES 0 Share on Twitter 0  Like on Facebook 0  Recommend on Google+ COMMENTS VIEW & COMMENT   JOIN THE DISCUSSION Sponsored Advertisement  Report ad    Comments  Community Avatar  inbox Recommend Recommend this discussion Sort by Newest    Be the first to comment. ALSO ON GIZMODO UK What Would It Take to Kill Daenerys' Dragons? What Would It Take to Kill Daenerys' Dragons? 4 comments • a day ago• mcborge1 — Nuke the site from orbit.. It's the only way to be sure. Scientists Discover How Diesel Could Pollute Less Than Petrol Scientists Discover How Diesel Could Pollute Less Than Petrol 1 comment • a day ago• thepillock — Yeah, new filters. Whoop.Those De-Pollution Filters are presumably of the types fittedto cars that meet the Euro4 and Euro5 standards, … Can Facebook Fix VR’s Most Frustrating Problems in One Fell Swoop? Can Facebook Fix VR’s Most Frustrating Problems in One Fell Swoop? 5 comments • a day ago• Sion12 — i dont think you can blame FB for theprice, especially if you look at vive and similarcompetitor whom all sell their device at similar … The Microsoft Font That Has Scandalised Pakistan's First Family The Microsoft Font That Has Scandalised Pakistan's First Family 1 comment • a day ago• strongp — I've no doubt that the rich will walk away(relatively) unscathed as usual. Which is why theywon't gеt any pity from me when in a few years … Powered by Disqus Powered by Disqus Subscribe Subscribe and get email updates from this discussion Add Disqus Privacy Gizmodo UK  POPULAR TAGS UK News Gadgets Design Watch This WTF Science Apple Android IFA 2015 Apps of the Week INTERNATIONAL Gizmodo US Gizmodo France Gizmodo Germany Gizmodo Italy Gizmodo Spain Gizmodo Poland Gizmodo Japan Gizmodo Australia Gizmodo Brazil OUR NETWORK Lifehacker UK Kotaku UK TechRadar GET IN TOUCH Contact us Send us a tip About us RSS Future Publishing Future Publishing ";
        HashMap <String,Double> curated_words = removeStopWords(str);
    }
}
