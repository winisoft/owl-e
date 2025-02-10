import React from 'react';

interface IconProps {
  icon: string; // The CSS class name for the icon
  className?: string; // Optional className for additional styling
  size?: number; // Optional size prop to control icon size
}

const Icon: React.FC<IconProps> = ({ icon, className = "", size = 24 }) => {
  return (
    <div
      className={`flex items-center justify-center ${className}`}
      style={{ width: size, height: size }} // Dynamically set size based on prop
    >
      <span className={icon} style={{ width: size, height: size }} /> {/* Apply size here too */}
    </div>
  );
};

export default Icon;
