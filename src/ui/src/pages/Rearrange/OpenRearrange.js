import React, {useState} from 'react';
import {Link, useParams} from 'react-router-dom';
import TextField from '@mui/material/TextField';
import useFetch, {withFetch} from '../../fetches/FetchChangeOneLetter';
import Autocomplete from '@mui/material/Autocomplete';
import Loader from '../../pages/Loader';
import Rearrange from '../../pages/Rearrange/Rearrange';

const OpenRearrange = (props) => {
    const {gameId} = useParams();
    let [properties, setProperties] = useState({})
    const [isLoading, setIsLoading] = useState(true);

    useFetch(
        '/api/games/rearrange/get/'+gameId,
        {},
        setProperties,
        setIsLoading,
        isLoading,
        ()=>{return true;}
    )

    if(!!properties && !!properties.points>=0 && !isLoading)
        return (
            <Rearrange {...properties} />
        );
    else
        return (<Loader />);

};

export default OpenRearrange;