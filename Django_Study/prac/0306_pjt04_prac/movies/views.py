from django.shortcuts import render
import random, requests

BASE_URL = "https://api.themoviedb.org/3"
# Create your views here.
def index(request):
    path = "/movie/now_playing"
    params = {
        "api_key":"use_your_own_api_key",
        "language":"ko",
        "page":1,
        "region":"KR",
    }
    # request
    response = requests.get(BASE_URL+path, params=params)
    data = response.json()["results"]
    # 영화목록
    movies_lst = {}
    for md in data:
        movies_lst[md.get("title")] = [md.get("overview")[:100]+"...", "https://image.tmdb.org/t/p/w500"+md.get("poster_path"), md.get("release_date")]

    context = {
        "movies_lst":movies_lst,
    }

    return render(request, 'movies/index.html', context)

def recommendations(request):
    movie_id = 278
    path = f"/movie/{movie_id}/recommendations"
    params = {
        "api_key":"use_your_own_api_key",
        "language":"ko",
        "page":1,
    }
    # request   
    response = requests.get(BASE_URL+path, params=params)
    data = random.choice(response.json()["results"])
    poster_path = "https://image.tmdb.org/t/p/original"+data["poster_path"]
    release_date = data["release_date"]
    overview = data["overview"]
    movie_title = data["title"]
    r_movie_id = data["id"]
    score = format(round(data["vote_average"], 2), ".1f") # 소숫점 둘째자리에서 반올림하여 첫째자리까지 표현
    context = {
        "poster_path":poster_path,
        "overview":overview,
        "movie_title":movie_title,
        "score":score,
        "release_date":release_date,
        "r_movie_id":r_movie_id,
    }
    return render(request, 'movies/recommendations.html', context)
