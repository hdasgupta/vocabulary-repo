import React, {useState, useEffect} from 'react';
import Word from '../../pages/ChangeOneLetter/Word'
import {withPostFetch} from '../../fetches/FetchChangeOneLetter';
import Loader from '../../pages/Loader';
import Dropdown from 'react-bootstrap/Dropdown';

const Words = (props) => {
    let update = {
        'gameId':props.gameId
    };

    let [properties, setProperties] = useState(props)

    const handleChange = (index, input) => {

        update = {
            ...update,
            index,
            input
        }

        properties = {};

        withPostFetch(
            '/api/games/change_one_letter/move',
            update,
            setProperties,
            ()=>{return true;}
        )
    };

    if(!!properties && !!properties.usedWords) {

        const selects = properties.usedWords[0].chars.map((chr, index)=> {
            if(properties.options[index].length>0) {
               return (
                    <Dropdown size='lg'>
                          <Dropdown.Toggle variant="primary" id={`dropdown_${index}`}>
                                {chr}
                          </Dropdown.Toggle>

                          <Dropdown.Menu>
                            {properties.options[index].map(opt=> {
                                return (
                                    <Dropdown.Item onClick={(e)=>handleChange(index, opt)}>{opt}</Dropdown.Item>
                                )
                            })}
                          </Dropdown.Menu>
                    </Dropdown>
               )
            } else {
                return (<center>{chr}</center>)
            }
        });

        const table = (
            <center>
                <table>
                    <tbody>
                        {properties.usedWords.map((word, index)=> {
                            if(index==0 && !properties.isClosed)
                                return (<tr>
                                   {
                                       word.chars.map((_, i)=> {
                                            return (
                                                <td width={`${Math.floor(100/(word.chars.length+1))}%`} class='char-class'>
                                                   <center>{selects[i]}</center>
                                               </td>
                                            )
                                       })
                                   }
                                   <td width={`${Math.floor(100/(word.chars.length+1))}%`}>
                                        <center>{word.isComputer?'🖥️':'🧑'}</center>
                                   </td>
                               </tr>);
                            else if(index == properties.usedWords.length - 1)
                                return (<Word word={word.chars} player={word.player} computer={word.isComputer} changedIndex={-1}/>);
                             else
                                return (<Word word={word.chars} player={word.player} computer={word.isComputer} changedIndex={getChange(word, properties.usedWords[index+1])}/>);
                        })}
                    </tbody>
                </table>
            </center>
        );

        return table;
    } else {
        return (<Loader />);
    }
};

function getChange(word1, word2) {
    return [...Array(word1.length).keys()].filter(index=>{
        return word1[index]!=word2[index]
    })[0]
}


export default Words;