import { useState, useEffect, useLayoutEffect } from 'react';
import axios from "axios";

const host = process.env.REACT_APP_HOST_URL

function useNewGame() {
    const [game, setGame] = useState({});

    useLayoutEffect(async()=> {
        const response = await fetch('/api/games/change_one_letter/new');
        const game = await response.json();
        setGame(game);
    });

    console.log(game);

    return game;
}

function useMove (gameId, index, char) {
    const [game, setGame] = useState({});

    useLayoutEffect(async()=> {
        const response = await fetch('/api/games/change_one_letter/move', {gameId, index, char});
        const game = await response.json();
        setGame(game);
    });

    return game;
}


function useFetch(url, args={}, setData, setIsLoading, isLoading, initialFetchCriteria) {
    useEffect(() => {
        if(initialFetchCriteria() && isLoading) {
            const fullUrl = `${host}${url}?${dataToUrl(args)}`;
            fetch(fullUrl)
            .then((response) => response.json())
            .then((data) => {
                setData(data)
                setIsLoading(false)
            })
            .catch((error) => console.log(error))
        }
    }, [isLoading])
 }

function usePostFetch(url, args={}, setData, setIsLoading, isLoading, initialFetchCriteria) {
 useEffect(() => {
     if(initialFetchCriteria() && isLoading) {
         const fullUrl = `${host}${url}?${dataToUrl(args)}`;
         fetch(fullUrl, {method: 'POST'})
         .then((response) => response.json())
         .then((data) => {
             setData(data)
             setIsLoading(false)
         })
         .catch((error) => console.log(error))
     }
 }, [isLoading])
}

function usePutFetch(url, args={}, setData, setIsLoading, isLoading, initialFetchCriteria) {
 useEffect(() => {
     if(initialFetchCriteria() && isLoading) {
         const fullUrl = `${host}${url}?${dataToUrl(args)}`;
         fetch(fullUrl, {method: 'PUT'})
         .then((response) => response.json())
         .then((data) => {
             setData(data)
             setIsLoading(false)
         })
         .catch((error) => console.log(error))
     }
 }, [isLoading])
}

function usePostBodyFetch(url, args={}, setData, setIsLoading, isLoading, initialFetchCriteria) {
 useEffect(() => {
     if(initialFetchCriteria() && isLoading) {
         const fullUrl = `${host}${url}`;
         fetch(
            fullUrl,
            {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(args)
            }
         )
         .then((response) => response.json())
         .then((data) => {
             setData(data)
             setIsLoading(false)
         })
         .catch((error) => console.log(error))
     }
 }, [isLoading])
}

 function withGetFetch(url, args={}, setData, initialFetchCriteria) {
     //useEffect(() => {
         if(initialFetchCriteria()) {
             const fullUrl = `${host}${url}?${dataToUrl(args)}`;
             fetch(fullUrl)
             .then((response) => response.json())
             .then((data) => {
                 setData(data)
             })
             .catch((error) => console.log(error))
         }
     //}, [isLoading])
  }

function withPutFetch(url, args={}, setData, initialFetchCriteria) {
 //useEffect(() => {
     if(initialFetchCriteria()) {
         const fullUrl = `${host}${url}?${dataToUrl(args)}`;
         fetch(fullUrl, {method: 'PUT'})
         .then((response) => response.json())
         .then((data) => {
             setData(data)
         })
         .catch((error) => console.log(error))
     }
 //}, [isLoading])
}

function withPostFetch(url, args={}, setData, initialFetchCriteria) {
   //useEffect(() => {
       if(initialFetchCriteria()) {
           const fullUrl = `${host}${url}?${dataToUrl(args)}`;
           fetch(fullUrl, {method: 'POST'})
           .then((response) => response.json())
           .then((data) => {
               setData(data)
           })
           .catch((error) => console.log(error))
       }
   //}, [isLoading])
}

function withPostBodyFetch(url, args={}, setData, initialFetchCriteria) {
   //useEffect(() => {
       if(initialFetchCriteria()) {
           const fullUrl = `${host}${url}`;
           fetch(
            fullUrl,
            {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(args)
            })
           .then((response) => response.json())
           .then((data) => {
               setData(data)
           })
           .catch((error) => console.log(error))
       }
   //}, [isLoading])
}

 function dataToUrl(data) {
    return Object.keys(data).map(key=>`${key}=${data[key]}`).join('&')
 }

export {
    useNewGame,
    useMove,
    withGetFetch,
    withPutFetch,
    withPostFetch,
    withPostBodyFetch,
    usePutFetch,
    usePostFetch,
    usePostBodyFetch
 };
export default useFetch;
