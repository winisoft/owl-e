import React from 'react';

interface IconButtonProps {
  icon: string;
  className?: string;
  size?: number;
  onClick: (e: React.MouseEvent<HTMLButtonElement>) => void;
}

const IconButton: React.FC<IconButtonProps> = ({ icon, className ="", size = 24, onClick }) => {
  return (
    <button
      onClick={onClick}
      style={{ width: size, height: size }}
      className={`relative w-6 h-6 flex items-center justify-center overflow-hidden ${className}
        transition-all duration-200 ease-in-out hover:bg-gray-700 active:bg-gray-600
        hover:shadow-md active:shadow-lg`}
    >
      <span style={{ width: size, height: size }} className={`${icon} transition-all transform hover:scale-105 active:scale-110`} />
    </button>
  );
};

export default IconButton;
