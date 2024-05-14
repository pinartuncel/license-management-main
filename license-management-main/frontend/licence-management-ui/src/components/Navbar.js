import {routes} from "./../config";
import Companies from "./Companies";

const Navbar = () => {
    return (
        <nav className='navbar bg-gray-300'>
            <div className='container'>
                <ul className='nav'>
                    <li>
                        <a href={routes.companies}>Companies</a>
                    </li>
                    <li>
                        <a href={routes.contracts}>Contracts</a>
                    </li>
                    <li>
                        <a href={routes.users}>Users</a>
                    </li>
                </ul>
            </div>
        </nav>
    );
};

export default Navbar;
