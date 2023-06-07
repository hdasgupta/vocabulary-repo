import React, {useState} from 'react';
import {Link, useParams} from 'react-router-dom';
import useFetch, {usePostFetch} from '../../fetches/FetchChangeOneLetter';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import Loader from '../../pages/Loader';
import Rearrange from '../../pages/Rearrange/Rearrange';

const NewRearrange = (props) => {
    let [properties, setProperties] = useState({})
    const [isLoading, setIsLoading] = useState(true)

    usePostFetch(
        '/api/games/rearrange/new',
        {},
        setProperties,
        setIsLoading,
        isLoading,
        ()=>{return true;}
    )


    if(!!properties && properties.points>=0 && !isLoading)
        return (
            <Rearrange {...properties} />
        );
    else
        return (<Loader />);
};

export default NewRearrange;