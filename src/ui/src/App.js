import './App.css';
import Layout from './pages/Layout';
import Home from './pages/Home';
import Loader from './pages/Loader';
import NewChangeOneLetter from './pages/ChangeOneLetter/NewChangeOneLetter';
import ListChangeOneLetter from './pages/ChangeOneLetter/ListChangeOneLetter';
import OpenChangeOneLetter from './pages/ChangeOneLetter/OpenChangeOneLetter';
import NewGuessTheWord from './pages/GuessTheWord/NewGuessTheWord';
import ListGuessTheWord from './pages/GuessTheWord/ListGuessTheWord';
import OpenGuessTheWord from './pages/GuessTheWord/OpenGuessTheWord';
import NewRearrange from './pages/Rearrange/NewRearrange';
import ListRearrange from './pages/Rearrange/ListRearrange';
import OpenRearrange from './pages/Rearrange/OpenRearrange';
import NewCrossWordJob from './pages/CrossWord/Jobs/NewCrossWordJob';
import ListCrossWordJob from './pages/CrossWord/Jobs/ListCrossWordJob';
import ListCrossWordJobBoards from './pages/CrossWord/Jobs/ListCrossWordJobBoards';
import OpenCrossWordJob from './pages/CrossWord/Jobs/OpenCrossWordJob';
import {BrowserRouter, Routes, Route, defer} from 'react-router-dom';

function App() {
  return (
    <div className="App">
        <BrowserRouter>
            <Routes>
                <Route path={'/'} element={<Layout/>}>
                    <Route index element={<Home/>}/>
                    <Route path={'change-one-letter/'}>
                        <Route path={"new/"} element={<NewChangeOneLetter/>}/>
                        <Route path={'open/'}>
                            <Route path={'pages/:size/:index'} element={<ListChangeOneLetter/>}/>
                            <Route path={'run/:gameId/'}>
                                <Route index element={<OpenChangeOneLetter/>}/>
                            </Route>
                        </Route>
                    </Route>
                    <Route path={'guess-the-word/'}>
                        <Route path={"new/:size"} element={<NewGuessTheWord/>}/>
                        <Route path={'open/'}>
                            <Route path={'pages/:size/:index'} element={<ListGuessTheWord/>}/>
                            <Route path={'run/:gameId/'} element={<OpenGuessTheWord/>}/>
                        </Route>
                    </Route>
                    <Route path={'rearrange/'}>
                        <Route path={"new"} element={<NewRearrange/>}/>
                        <Route path={'open/'}>
                            <Route path={'pages/:size/:index'} element={<ListRearrange/>}/>
                            <Route path={'run/:gameId/'} element={<OpenRearrange/>}/>
                        </Route>
                    </Route>
                    <Route path={'crossword/'}>
                        <Route path={'jobs/'}>
                            <Route path={"create"} element={<NewCrossWordJob/>}/>
                            <Route path={'open/'}>
                                <Route path={'pages/:size/:index'} element={<ListCrossWordJob/>}/>
                                <Route path={'run/:jobId/'} element={<OpenCrossWordJob/>}/>
                            </Route>
                            <Route path={'boards/:jobId/:index'} element={<ListCrossWordJobBoards/>}/>
                        </Route>
                    </Route>
                </Route>
            </Routes>
        </BrowserRouter>
    </div>
  );
};

export default App;
