import React,{ useState } from 'react';
import {useParams } from "react-router-dom"
import useFetch from '../../fetches/FetchChangeOneLetter';
import Words from '../../pages/ChangeOneLetter/Words';
import Loader from '../../pages/Loader';

const OpenChangeOneLetter = (props)=> {
    const {gameId} = useParams();
    const [data, setData] = useState(props)

    const [isLoading, setIsLoading] = useState(true);

    useFetch(
        `/api/games/change_one_letter/get/${gameId}`,
        {},
        setData,
        setIsLoading,
        isLoading,
        ()=>{return true}
    );

    console.log(data);
    console.log(isLoading);

    if(!!data.id && !isLoading)
        return <Words gameId={data.id} usedWords={data.usedWords} options={data.options} isClosed={data.isClosed}/>
    else
        return (<Loader />)
}

export default OpenChangeOneLetter;