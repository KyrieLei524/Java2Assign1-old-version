import java.awt.image.ImageProducer;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class MovieAnalyzer {

  public static class Movie {
    private String Poster_Link;
    private String Series_Title;
    private String Released_Year;
    private String Certificate;
    private String Runtime;
    private String Genre;
    public String IMDB_Rating;
    private String Overview;
    private String Meta_score;
    private String Director;
    private String Star1;
    private String Star2;
    private String Star3;
    private String Star4;
    private String No_of_Votes;
    public String Gross = null;


    public Long gross(){
      if(!Objects.equals(Gross, "")) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(Gross);
        String longtime = m.replaceAll("").trim();
        return Long.parseLong(longtime);
      }
      else return null;
    }

    public Movie(String poster_Link, String series_Title, String released_Year, String certificate, String runtime, String genre, String IMDB_Rating, String overview, String meta_score, String director, String star1, String star2, String star3, String star4, String no_of_Votes, String gross) {
      this.Poster_Link = poster_Link;
      this.Series_Title = series_Title;
      this.Released_Year = released_Year;
      this.Certificate = certificate;
      this.Runtime = runtime;
      this.Genre = genre;
      this.IMDB_Rating = IMDB_Rating;
      this.Overview = overview;
      this.Meta_score = meta_score;
      this.Director = director;
      this.Star1 = star1;
      this.Star2 = star2;
      this.Star3 = star3;
      this.Star4 = star4;
      this.No_of_Votes = no_of_Votes;
      this.Gross = gross;
    }

    public String getPoster_Link() {
      return Poster_Link;
    }

    public String getSeries_Title() {
      return Series_Title;
    }

    public Integer getReleased_Year() {
      return Integer.parseInt(Released_Year);
    }

    public String getCertificate() {
      return Certificate;
    }

    public String getRuntime() {
      return Runtime;
    }

    public String getGenre() {
      return Genre;
    }

    public String getIMDB_Rating() {
      return IMDB_Rating;
    }

    public String getOverview() {
      return Overview;
    }

    public String getMeta_score() {
      return Meta_score;
    }

    public String getDirector() {
      return Director;
    }

    public String getStar1() {
      return Star1;
    }

    public String getStar2() {
      return Star2;
    }

    public String getStar3() {
      return Star3;
    }

    public String getStar4() {
      return Star4;
    }

    public String getNo_of_Votes() {
      return No_of_Votes;
    }

    public String getGross() {
      return Gross;
    }

    public int time(){
      String regEx = "[^0-9]";
      Pattern p = Pattern.compile(regEx);
      Matcher m = p.matcher(Runtime);
      String longtime = m.replaceAll("").trim();
      return Integer.parseInt(longtime);
    }


    @Override
    public String toString() {
      return "Movie{"
                +
                "Poster_Link='"
                + Poster_Link
                + '\''
                +
                ", Series_Title='"
                + Series_Title
                + '\''
                +
                ", Released_Year='"
                + Released_Year
                + '\''
                +
                ", Certificate='"
                + Certificate
                + '\''
                +
                ", Runtime='"
                + Runtime
                + '\''
                +
                ", Genre='"
                + Genre + '\''
                +
                ", IMDB_Rating='"
                + IMDB_Rating
                + '\''
                +
                ", Overview='"
                + Overview
                + '\''
                +
                ", Meta_score='"
                + Meta_score
                + '\''
                +
                ", Director='"
                + Director
                + '\''
                +
                ", Star1='"
                + Star1
                + '\''
                +
                ", Star2='"
                + Star2
                + '\''
                +
                ", Star3='"
                + Star3
                + '\''
                +
                ", Star4='"
                + Star4
                + '\''
                +
                ", No_of_Votes='"
                + No_of_Votes
                + '\''
                +
                ", Gross='"
                + Gross
                + '\''
                +
                '}';
    }
}

    static ArrayList<Movie> movieList = new ArrayList<>();
    String[] stringList = new String[16];
    public MovieAnalyzer(String dataset_path) throws IOException {


    BufferedReader br = null;
    String line = "";
    br = new BufferedReader(new InputStreamReader(new FileInputStream(dataset_path), StandardCharsets.UTF_8));

    Pattern pattern = Pattern.compile("(,)?((\"[^\"]*(\"{2})*[^\"]*\")*[^,]*)");

    line = br.readLine();
    while ((line = br.readLine()) != null) {
      Matcher matcher = pattern.matcher(line);
      int i = 0;
      while(matcher.find()) {
        String cell = matcher.group(2);//group(2) is ((\"[^\"]*(\"{2})*[^\"]*\")*[^,]*)
        Pattern pattern2 = Pattern.compile("\"((.)*)\"");
        Matcher matcher2 = pattern2.matcher(cell);
        if(matcher2.find()) {
          cell = matcher2.group(1);
        }		//... get the cell and you analysis
        if(i < 16)stringList[i++] = cell;
      }
      movieList.add(new Movie(stringList[0], stringList[1], stringList[2], stringList[3], stringList[4], stringList[5], stringList[6], stringList[7], stringList[8], stringList[9], stringList[10], stringList[11], stringList[12], stringList[13], stringList[14], stringList[15]));
    }
  }

    public Map<Integer, Integer> getMovieCountByYear(){
        Map<Integer, Long> mapp =  movieList.
                stream().
                collect(
                        Collectors.groupingBy(
                                Movie::getReleased_Year,
                                Collectors.counting()
                        )
                );
        Map<Integer, Integer> map = new HashMap<>();
        mapp.forEach((k,v)->{
            map.put(k,v.intValue());
        });
    Map<Integer, Integer> result = new LinkedHashMap<>();
    map.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByKey())).forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
    return result;
  }


  public Map<String, Integer> getMovieCountByGenre(){
    ArrayList<String> genreList = new ArrayList<>();
      for(int i = 0; i < movieList.size(); i++){
        if(!movieList.get(i).getGenre().equals("")) {
          String[] x = movieList.get(i).getGenre().split(", ");
          for(int j = 0; j < x.length; j++){
            genreList.add(x[j]);
          }
        }
      }
      Map<String, Long> mapp = genreList.
        stream().collect(Collectors.groupingBy(String::toString,
        Collectors.counting()
      ));
      List<Map.Entry<String, Long>> list = new ArrayList<Map.Entry<String, Long>>(mapp.entrySet());
      Collections.sort(list, new Comparator<>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                int re = o2.getValue().compareTo(o1.getValue());
                if (re!=0){return re; }
                else{return o1.getKey().compareTo(o2.getKey());}
      }
    });
    Map<String, Integer> result = new LinkedHashMap<>();
    for (Map.Entry<String, Long> entry : list){
      result.put(entry.getKey(), entry.getValue().intValue());
  }
    return result;
  }

    ArrayList<List<String>> arr = new ArrayList<>();
    public Map<List<String>, Integer> getCoStarCount(){
        for(int i = 0; i < movieList.size(); i++){
            String star1 = movieList.get(i).getStar1();
            String star2 = movieList.get(i).getStar2();
            String star3 = movieList.get(i).getStar3();
            String star4 = movieList.get(i).getStar4();

      List<String> s1 = new ArrayList<>();
      s1.add(star1);
      s1.add(star2);
      List<String> s2 = new ArrayList<>();
      s2.add(star1);
      s2.add(star3);
      List<String> s3 = new ArrayList<>();
      s3.add(star1);
      s3.add(star4);
      List<String> s4 = new ArrayList<>();
      s4.add(star2);
      s4.add(star3);
      List<String> s5 = new ArrayList<>();
      s5.add(star2);
      s5.add(star4);
      List<String> s6 = new ArrayList<>();
      s6.add(star3);
      s6.add(star4);
      Collections.sort(s1);
      Collections.sort(s2);
      Collections.sort(s3);
      Collections.sort(s4);
      Collections.sort(s5);
      Collections.sort(s6);

            if(!s1.get(0).equals("") && !s1.get(1).equals(""))            arr.add(s1);
            if(!s2.get(0).equals("") && !s2.get(1).equals(""))            arr.add(s2);
            if(!s3.get(0).equals("") && !s3.get(1).equals(""))            arr.add(s3);
            if(!s4.get(0).equals("") && !s4.get(1).equals(""))            arr.add(s4);
            if(!s5.get(0).equals("") && !s5.get(1).equals(""))            arr.add(s5);
            if(!s6.get(0).equals("") && !s6.get(1).equals(""))            arr.add(s6);
        }
        Map<List<String>, Long> mapp = arr.
                stream().collect(Collectors.groupingBy(strings -> strings,
                        Collectors.counting()
                ));
        List<Map.Entry<List<String>, Long>> list = new ArrayList<Map.Entry<List<String>,Long>>(mapp.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<List<String>, Long>>() {
            @Override
            public int compare(Map.Entry<List<String>, Long> o1, Map.Entry<List<String>, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        Map<List<String>, Integer> map = new LinkedHashMap<List<String>, Integer>();
        for(Map.Entry<List<String>, Long> entry : list){
            map.put(entry.getKey(), entry.getValue().intValue());
        }
        return map;
    }

  public List<String> getTopMovies(int top_k, String by){
    List<String> result = new ArrayList<>();
    if(by.equals("runtime")){
        Map<String, Integer> topTime = new IdentityHashMap<>();

      for (int i = 0;i < movieList.size(); i++){
        String title = movieList.get(i).Series_Title;
        String time = movieList.get(i).Runtime;
        if(time != null && title != null) {
          String regEx = "[^0-9]";
          Pattern p = Pattern.compile(regEx);
          Matcher m = p.matcher(time);
          String longtime = m.replaceAll("").trim();
          int timee = Integer.parseInt(longtime);
          topTime.put(title, timee);
        }
      }
      List<Map.Entry<String, Integer>> mapp = new ArrayList<>(topTime.entrySet());
      Collections.sort(mapp, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                int re = o2.getValue().compareTo(o1.getValue());
                if(re!=0){return re;}
                else{return o1.getKey().compareTo(o2.getKey());}
            }
      });
      for(int i = 0; i < top_k; i++){
        result.add(mapp.get(i).getKey());
      }
    }
    if(by.equals("overview")){
      Map<String, String> topo = new IdentityHashMap<>();
      for(int i = 0;i < movieList.size(); i++){
        String title = movieList.get(i).Series_Title;
        String overview = movieList.get(i).Overview;
        if(overview != null && title != null) {
          topo.put(title, overview);
        }
      }
      List<Map.Entry<String, String>> mapp = new ArrayList<>(topo.entrySet());
      Collections.sort(mapp, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                int re = o2.getValue().length() - o1.getValue().length();
                if(re!=0){return re;}
                else{return o1.getKey().compareTo(o2.getKey());}
            }
      });
      for(int i = 0; i < top_k; i++){
        result.add(mapp.get(i).getKey());
      }
    }
    return result;
  }

  public List<String> getTopStars(int top_k, String by){
    List<String> result = new ArrayList<>();
    if(by.equals("rating")){
        Map<String,Float> mapp = new IdentityHashMap<>();
        for(int i = 0; i < movieList.size(); i++){
            mapp.put(movieList.get(i).getStar1(), Float.parseFloat(movieList.get(i).IMDB_Rating));
            mapp.put(movieList.get(i).getStar2(), Float.parseFloat(movieList.get(i).IMDB_Rating));
            mapp.put(movieList.get(i).getStar3(), Float.parseFloat(movieList.get(i).IMDB_Rating));
            mapp.put(movieList.get(i).getStar4(), Float.parseFloat(movieList.get(i).IMDB_Rating));
        }
        Map<String, Double> map = mapp.entrySet().stream().collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.averagingDouble(Map.Entry::getValue)));
        List<Map.Entry<String, Double>> mappp = new ArrayList<>(map.entrySet());
        Collections.sort(mappp, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                int re = o2.getValue().compareTo(o1.getValue());
                if(re!=0){return re;}
                else{return o1.getKey().compareTo(o2.getKey());}
            }
        });
      for (int i = 0; i < top_k; i++){
        result.add(mappp.get(i).getKey());
      }

    }
    if(by.equals("gross")){
      Map<String, Long> mapp = new IdentityHashMap<>();
      for(int i = 0; i < movieList.size(); i++){
        if(movieList.get(i).Gross.equals(""))continue;
        mapp.put(movieList.get(i).getStar1(), movieList.get(i).gross());
        mapp.put(movieList.get(i).getStar2(), movieList.get(i).gross());
        mapp.put(movieList.get(i).getStar3(), movieList.get(i).gross());
        mapp.put(movieList.get(i).getStar4(), movieList.get(i).gross());
      }
      Map<String, Double> map = mapp.entrySet().stream().collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.averagingDouble(Map.Entry::getValue)));
      List<Map.Entry<String, Double>> mappp = new ArrayList<>(map.entrySet());
      Collections.sort(mappp, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                int re = o2.getValue().compareTo(o1.getValue());
                if(re!=0){return re;}
                else{return o1.getKey().compareTo(o2.getKey());}
            }
        });
      for(int i = 0; i < top_k; i++){
        result.add(mappp.get(i).getKey());
      }
    }
    return result;
  }

  public List<String> searchMovies(String genre, float min_rating, int max_runtime){
    List<Movie> result1 =         movieList.stream().filter(w -> w.getGenre().contains(genre)).
            filter(w -> Float.parseFloat(w.IMDB_Rating) >= min_rating).
            filter(w -> w.time() <= max_runtime).
            collect(Collectors.toList());
    List<String> result = result1.stream().map(w -> w.getSeries_Title()).collect(Collectors.toList());
    Collections.sort(result);
    return result;
  }

  public static void main(String[] args) throws IOException {
    MovieAnalyzer a = new MovieAnalyzer("resources/imdb_top_500.csv");
  }


}