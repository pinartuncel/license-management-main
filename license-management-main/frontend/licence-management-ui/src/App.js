import {Redirect, Route, Switch, useHistory} from 'react-router-dom';
import Header from './components/Header';
import Navbar from './components/Navbar';
import Companies from './components/Companies';
import AddCompany from './components/AddCompany';
import Contracts from "./components/Contracts";
import AddContract from "./components/AddContract";
import Users from "./components/Users";
import Login from './components/Login';


import axios from "axios";
import {routes} from "./config";
import Bottombar from "./components/Bottombar";
import {useState} from "react";
import EditUser from "./components/EditUser";
import Details from "./components/Details";
import AddUser from "./components/AddUser";
import EditCompany from "./components/EditCompany";
import EditContract from "./components/EditContract";
import UsersByCompany from "./components/UsersByCompany";
import ContractsByCompany from "./components/ContractsByCompany";

function App() {
    const history = useHistory();
    const [headTitle, setHeadTitle] = useState('');
    const [showHeadAddBtn, setShowHeadAddBtn] = useState('');
    const [filterText, setFilterText] = useState('');

    axios.interceptors.response.use(
        (response) => {
            return response;
        },
        (error) => {
            if (error.response?.status === 415) {
                return Promise.reject(error);
            }
            history.push(routes.login);
            return new Promise(() => {
            });
        }
    );
    axios.defaults.withCredentials = true;

    const getHeadTitle = (text) => {
        setHeadTitle(text);
    }

    const showAddInHeader = (visible) => {
        setShowHeadAddBtn(visible);
    }

    const onClickAdd = () => {
        switch (headTitle) {
            case 'Companies':
                history.push(routes.addcompany);
                break;
            case 'Users':
                history.push(routes.adduser);
                break;
            case 'Contracts':
                history.push(routes.addcontract);
                break;
        }
    }

    const showUserPrefs = () => {
        let actUser = JSON.parse(localStorage.getItem('user'));
        actUser && history.push(`${routes.edituser}/${actUser.id}`);
    }

    return (
        <div className='my-container'>
            <Header title={headTitle} onClickAdd={onClickAdd} showAdd={showHeadAddBtn}
                    onClickUserPrefs={showUserPrefs} setFilterText={setFilterText}/>
            <Navbar/>
            <Switch>
                <Route path={routes.companies} exact
                       component={() => <Companies func={getHeadTitle} showAdd={showAddInHeader}
                                                   searchText={filterText}/>}/>
                <Route path={routes.contracts} exact
                       component={() => <Contracts func={getHeadTitle} showAdd={showAddInHeader}
                                                   searchText={filterText}/>}/>
                <Route path={routes.users} exact
                       component={() => <Users func={getHeadTitle} showAdd={showAddInHeader}
                                               searchText={filterText}/>}/>
                <Route path={routes.login} exact
                       component={() => <Login func={getHeadTitle} showAdd={showAddInHeader}/>}/>
                <Route path={`${routes.edituser}/:id?`} exact
                       component={() => <EditUser func={getHeadTitle} showAdd={showAddInHeader}/>}/>
                <Route path={routes.adduser} exact
                       component={() => <AddUser func={getHeadTitle} showAdd={showAddInHeader}/>}/>
                <Route path={`${routes.usersbycompany}/:id?`} exact
                       component={() => <UsersByCompany func={getHeadTitle} showAdd={showAddInHeader}/>}/>
                <Route path={`${routes.details}/:id?`} exact
                       component={() => <Details func={getHeadTitle} showAdd={showAddInHeader}/>}/>
                <Route path={routes.addcompany} exact
                       component={() => <AddCompany func={getHeadTitle} showAdd={showAddInHeader}/>}/>
                <Route path={`${routes.editcompany}/:id?`} exact
                       component={() => <EditCompany func={getHeadTitle} showAdd={showAddInHeader}/>}/>
                <Route path={`${routes.editcontract}/:id?`} exact
                       component={() => <EditContract func={getHeadTitle} showAdd={showAddInHeader}/>}/>
                <Route path={routes.addcontract} exact
                       component={() => <AddContract func={getHeadTitle} showAdd={showAddInHeader}/>}/>
                <Route path={`${routes.contractsbycompany}/:id?`} exact
                       component={() => <ContractsByCompany func={getHeadTitle} showAdd={showAddInHeader}/>}/>

                <Redirect to={routes.companies}/>
            </Switch>
            <Bottombar/>
        </div>

    );
}

export default App;
