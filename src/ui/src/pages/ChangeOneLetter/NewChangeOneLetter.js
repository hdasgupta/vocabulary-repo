import React,{ useState, useLayoutEffect } from 'react';
import {usePostFetch} from '../../fetches/FetchChangeOneLetter';
import Words from '../../pages/ChangeOneLetter/Words';
import Loader from '../../pages/Loader';

const NewChangeOneLetter = (props)=> {
    const [data, setData] = useState(props)

    const [isLoading, setIsLoading] = useState(true);

    usePostFetch(
        '/api/games/change_one_letter/new',
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

export default NewChangeOneLetter;