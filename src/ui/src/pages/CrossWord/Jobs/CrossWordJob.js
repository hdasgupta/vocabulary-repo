import React, {useState} from 'react';
import {Link, useParams} from 'react-router-dom';
import {range} from '../../../common/Methods';


const CrossWordJob = (props) => {
    return (
        <table>
            <tbody>
                <tr>
                    <th rowSpan={props.words.length}>
                        Words
                    </th>
                    <td>
                    {
                        props.words[0].word
                    }
                    </td>
                    <td>
                    {
                        props.words[0].desc
                    }
                    </td>
                </tr>
                {
                    range(1, props.words.length -1)
                        .map(index=>{
                            return (
                                <tr>
                                    <td>
                                    {
                                        props.words[index].word
                                    }
                                    </td>
                                    <td>
                                    {
                                        props.words[index].desc
                                    }
                                    </td>
                                </tr>
                            )
                        })
                }
                <tr>
                    <th>Variety</th>
                    <td colSpan="2">{props.options}</td>
                </tr>
                <tr>
                    <th>Status</th>
                    <td colSpan="2">{props.status}</td>
                </tr>
                <tr>
                    <th>Message</th>
                    <td colSpan="2">{props.message}</td>
                </tr>
                <tr>
                    <th>Last Updated</th>
                    <td colSpan="2">{props.lastUpdated}</td>
                </tr>
                {
                    props.status === "Completed"?
                    (
                        <tr>
                            <td colSpan="3">
                                <Link to={'/crossword/jobs/boards/'+props.id+'/0'} className="btn btn-primary">List Boards</Link>
                            </td>
                        </tr>
                    ):
                    (
                        <>
                        </>
                    )

                }
            </tbody>
        </table>
    )
}

export default CrossWordJob