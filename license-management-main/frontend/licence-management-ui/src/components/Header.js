import {FiLogOut, FiPlusSquare, FiUser} from 'react-icons/fi';
import ButtonIco from './ButtonIco';
import {useHistory} from 'react-router-dom';
import {routes} from '../config';

const Header = ({title, onClickAdd, showAdd, onClickUserPrefs, setFilterText}) => {
    const history = useHistory();

    const iconSize = 30;
    const onClickLogout = () => {
        localStorage.removeItem('user');
        history.push(routes.login);
    };

    return (
        <header className='bg-blue-500'>
            <h1 className='px-2 font-bold text-white mb-4'>License Management</h1>

            <div className='header-container py-2'>
                <div className='group'>
                    <span className='hItem hText'>{title}</span>
                    <span className='hItem icon onHoverChangeCol'>
                        {showAdd &&
                            <ButtonIco
                                name={'Add'}
                                icon={<FiPlusSquare size={iconSize}/>}
                                onClick={onClickAdd}
                            />
                        }
          </span>
                </div>

                <div className='clearfix'></div>

                <div className='group'>
          <span className='hItem'>
              {showAdd &&
                  <input type='text' placeholder='Filter'
                         onChange={e => setFilterText(e.target.value)}/>

              }
          </span>
                    <span className='hItem icon onHoverChangeCol'>
            <ButtonIco
                name={'Profile'}
                icon={<FiUser size={iconSize}/>}
                onClick={onClickUserPrefs}
            />
          </span>
                    <span className='hItem icon onHoverChangeCol'>
            <ButtonIco
                name={'Logout'}
                icon={<FiLogOut size={iconSize}/>}
                onClick={onClickLogout}
            />
          </span>
                </div>
            </div>
        </header>
    );
};

export default Header;
