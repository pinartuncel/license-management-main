import PropTypes from 'prop-types';

const ButtonTxt = ({ name, onClick }) => {
  return (
    <button alt={name} onClick={onClick}>
      {name}
    </button>
  );
};

ButtonTxt.propTypes = {
  name: PropTypes.string,
  linkTo: PropTypes.string,
  onClick: PropTypes.func,
};

export default ButtonTxt;
