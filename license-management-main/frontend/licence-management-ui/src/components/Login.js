import axios from "axios";
import {APP_API_ENDPOINT_URL, routes} from "../config";
import {useHistory} from "react-router-dom";
import {useState} from "react";


const Login = (props) => {
    props.func('Login');
    props.showAdd(false);

    const history = useHistory();
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();

    const userInput = (e) => {
        setUsername(e.target.value);
    }

    const pwInput = (e) => {
        setPassword(e.target.value);
    }

    const btnLogin = () => {

        const credentials = JSON.stringify({
            username: username,
            password: password
        });

        axios.post(`${APP_API_ENDPOINT_URL}/login`, credentials, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then((response) => {
            if (response.status === 200) {
                localStorage.setItem("user", JSON.stringify(response.data))
                history.push(routes.companies)
            }
        });
    }


    return (
        <div className="flex h-screen">
            <div className="m-auto border border-indigo-800 text-center p-10">
                <h1 className="flex flex-col">Login</h1>
                <input
                    className="flex flex-col place-self-center border shadow-sm border-slate-300 focus:outline-none focus:border-sky-500 focus:ring-sky-500 text-center rounded-md h-14 flex flex-col px-4 py-2 m-2"
                    type='text' placeholder='User' value={username} onChange={userInput}/>
                <input
                    className="place-self-center border shadow-sm border-slate-300 focus:outline-none focus:border-sky-500 focus:ring-sky-500 text-center rounded-md h-14 px-4 py-2 m-2"
                    type='password' placeholder='Password' value={password} onChange={pwInput}/>
                <button type='button' onClick={btnLogin}
                        className="bg-blue-500 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm p-2.5 text-center inline-flex items-center mr-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                    <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M10.293 3.293a1 1 0 011.414 0l6 6a1 1 0 010 1.414l-6 6a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-4.293-4.293a1 1 0 010-1.414z"
                        ></path>
                    </svg>
                </button>
            </div>
        </div>
    );
};

export default Login;
