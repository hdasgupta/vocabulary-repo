import React, {useState} from 'react';
import {Link, useParams} from 'react-router-dom';
import Button from 'react-bootstrap/Button';
import useFetch, {withFetch} from '../../fetches/FetchChangeOneLetter';
import RearrangeInput from '../../pages/Rearrange/RearrangeInput'
import Loader from '../../pages/Loader';

const RearrangeMatch = (props) => {
    const {guessedWords} = props;

    return (
        <table>
            <tbody>
            {
                guessedWords.map(word=> {
                    return (
                        <tr>
                        {
                            Array.from(word).map(chr=> {
                                return (
                                    <td align="center">
                                    {
                                        (
                                            <>
                                            {
                                                chr
                                            }
                                            </>
                                        )
                                    }

                                    </td>
                                )
                            })
                        }
                        </tr>
                    )
                })
            }
            </tbody>
        </table>
    );

};

export default RearrangeMatch;