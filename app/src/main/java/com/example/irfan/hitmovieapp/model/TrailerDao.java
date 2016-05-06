package com.example.irfan.hitmovieapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by irfan on 06/05/16.
 */
public class TrailerDao implements Serializable {
    public boolean adult;
    public String backdrop_path;
    public BelongToCollections belongs_to_collection;
    public long budget;
    public List<Genres> genres;
    public String homepage;
    public int id;
    public String imdb_id;
    public String original_language;
    public String original_title;
    public String overview;
    public double popularity;
    public String poster_path;
    public List<ProductionCompanies> production_companies;
    public List<ProductionCountries> production_countries;
    public String release_date;
    public long revenue;
    public int runtime;
    public List<SpokenLanguages> spoken_languages;
    public String status;
    public String tagline;
    public String title;
    public boolean video;
    public double vote_average;
    public int vote_count;
    public Trailers trailers;

    public boolean isAdult() {
        return adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public BelongToCollections getBelongs_to_collection() {
        return belongs_to_collection;
    }

    public long getBudget() {
        return budget;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getId() {
        return id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public List<ProductionCompanies> getProduction_companies() {
        return production_companies;
    }

    public List<ProductionCountries> getProduction_countries() {
        return production_countries;
    }

    public String getRelease_date() {
        return release_date;
    }

    public long getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public List<SpokenLanguages> getSpoken_languages() {
        return spoken_languages;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public Trailers getTrailers() {
        return trailers;
    }

    public class BelongToCollections implements Serializable {
        public int id;
        public String name;
        public String poster_path;
        public String backdrop_path;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }
    }

    public class Trailers implements Serializable {
        public List<Quicktime> quicktime;
        public List<Youtube> youtube;

        public List<Quicktime> getQuicktime() {
            return quicktime;
        }

        public List<Youtube> getYoutube() {
            return youtube;
        }

        public class Quicktime implements Serializable {

        }

        public class Youtube implements Serializable {
            public String name;
            public String size;
            public String source;
            public String type;

//            public Youtube(String name, String size, String source, String type){
//                this.name=name;
//                this.size=size;
//                this.source=source;
//                this.type=type;
//            }

            public String getName() {
                return name;
            }

            public String getSize() {
                return size;
            }

            public String getSource() {
                return source;
            }

            public String getType() {
                return type;
            }
        }
    }

    public class SpokenLanguages {
        public String iso_639_1;
        public String name;

        public String getIso_639_1() {
            return iso_639_1;
        }

        public String getName() {
            return name;
        }
    }

    public class ProductionCountries {
        public String iso_3166_1;
        public String name;

        public String getIso_3166_1() {
            return iso_3166_1;
        }

        public String getName() {
            return name;
        }
    }

    public class ProductionCompanies implements Serializable {
        public String name;
        public int id;

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }

    public class Genres {
        public int id;
        public String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

}
