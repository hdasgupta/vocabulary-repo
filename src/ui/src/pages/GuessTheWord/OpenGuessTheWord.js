import React, {useState} from 'react';
import {Link, useParams} from 'react-router-dom';
import TextField from '@mui/material/TextField';
import useFetch, {withGetFetch} from '../../fetches/FetchChangeOneLetter';
import Autocomplete from '@mui/material/Autocomplete';
import Loader from '../../pages/Loader';
import GuessTheWord from '../../pages/GuessTheWord/GuessTheWord';

const OpenGuessTheWord = (props) => {
    const {gameId} = useParams();
    let [properties, setProperties] = useState({})
    const [isLoading, setIsLoading] = useState(true);

    useFetch(
        '/api/games/guess-the-word/get/'+gameId,
        {},
        setProperties,
        setIsLoading,
        isLoading,
        ()=>{return true;}
    )

    if(!!properties && !!properties.point && !isLoading)
        return (
            <GuessTheWord {...properties} />
        );
    else
        return (<Loader />);

};

export default OpenGuessTheWord;