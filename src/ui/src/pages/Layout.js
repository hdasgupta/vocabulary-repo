import '../pages/Loader.css'
import React,{ useState }  from 'react';
import {Link, Outlet} from 'react-router-dom';
import { faBars, faArrowLeft} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Button from 'react-bootstrap/Button';
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import Offcanvas from 'react-bootstrap/Offcanvas';
import Accordion from 'react-bootstrap/Accordion';
import {range} from '../common/Methods';


const Layout = () => {
    const [show, setShow] = useState(true);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const handleAutoHide = (e) => {
        e.preventDefault();
        setShow(false);
        e.run();
    };

    return (
        <>
            <Button variant="primary" onClick={handleShow}>
                <FontAwesomeIcon  className="icon" icon={faBars} />
            </Button>

            <Offcanvas show={show} onHide={handleClose}>
                <Offcanvas.Header closeButton>
                    <Offcanvas.Title>
                        <Link to={''} className={"btn btn-primary"}>
                            <FontAwesomeIcon  className="icon" icon={faArrowLeft} />
                            &nbsp;&nbsp;&nbsp;
                            Vocabulary Games
                        </Link>
                    </Offcanvas.Title>
                </Offcanvas.Header>
                <Offcanvas.Body>
                    <Accordion defaultActiveKey="0">
                        <Accordion.Item eventKey="0">
                            <Accordion.Header>Change One Letter</Accordion.Header>
                            <Accordion.Body>
                                <ButtonGroup style={{width:'100%'}} vertical aria-label="Change One Letter">
                                    <Link to={'change-one-letter/new'} className={"btn btn-primary"}>New Game</Link>
                                    <Link to={'change-one-letter/open/pages/small/0'} className={"btn btn-primary"}>List Game</Link>
                                </ButtonGroup>
                            </Accordion.Body>
                        </Accordion.Item>
                        <Accordion.Item eventKey="1">
                            <Accordion.Header>Guess The Word</Accordion.Header>
                            <Accordion.Body>
                                <Accordion defaultActiveKey="0">
                                    <Accordion.Item eventKey="0">
                                        <Accordion.Header>New</Accordion.Header>
                                        <Accordion.Body>
                                            <ButtonGroup style={{width:'100%'}} vertical aria-label="Guess The Word">
                                                {
                                                    range(4, 7)
                                                        .map(size => {
                                                            return (
                                                                <Link to={'guess-the-word/new/'+size} className={"btn btn-primary"}>
                                                                    With {size} Letter Word
                                                                 </Link>
                                                            )
                                                        })
                                                }
                                            </ButtonGroup>
                                        </Accordion.Body>
                                    </Accordion.Item>
                                </Accordion>
                                <Link to={'guess-the-word/open/pages/small/0'}  style={{width:'100%'}} className={"btn btn-primary"}>
                                    List Game
                                </Link>
                            </Accordion.Body>
                        </Accordion.Item>
                        <Accordion.Item eventKey="2">
                            <Accordion.Header>Rearrange</Accordion.Header>
                            <Accordion.Body>
                                <ButtonGroup style={{width:'100%'}} vertical aria-label="Rearrange">
                                    <Link to={'rearrange/new'} className={"btn btn-primary"}>New Game</Link>
                                    <Link to={'rearrange/open/pages/small/0'} className={"btn btn-primary"}>List Game</Link>
                                </ButtonGroup>
                            </Accordion.Body>
                        </Accordion.Item>
                        <Accordion.Item eventKey="2">
                            <Accordion.Header>Crossword</Accordion.Header>
                            <Accordion.Body>
                                <Accordion defaultActiveKey="0">
                                    <Accordion.Item eventKey="0">
                                        <Accordion.Header>Jobs</Accordion.Header>
                                        <Accordion.Body>
                                            <ButtonGroup style={{width:'100%'}} vertical aria-label="Guess The Word">
                                                <Link to={'crossword/jobs/create'} className={"btn btn-primary"}>Create Boards Job</Link>
                                                <Link to={'crossword/jobs/open/pages/small/0'} className={"btn btn-primary"}>List Job</Link>
                                            </ButtonGroup>
                                        </Accordion.Body>
                                    </Accordion.Item>
                                </Accordion>
                            </Accordion.Body>
                        </Accordion.Item>
                    </Accordion>
                </Offcanvas.Body>
            </Offcanvas>

            <div id={'main'} className={'loader-div'}>
                <center>
                    <Outlet/>
                </center>
            </div>
        </>
    )
};

export default Layout;