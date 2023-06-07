import React, {useState} from 'react';
import {Link, useParams} from 'react-router-dom';
import useFetch, {usePostFetch} from '../../fetches/FetchChangeOneLetter';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import Loader from '../../pages/Loader';
import GuessTheWord from '../../pages/GuessTheWord/GuessTheWord';

const NewGuessTheWord = (props) => {
    const {size} = useParams();
    let [properties, setProperties] = useState({})
    const [isLoading, setIsLoading] = useState(true)

    usePostFetch(
        '/api/games/guess-the-word/new/'+size,
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

export default NewGuessTheWord;