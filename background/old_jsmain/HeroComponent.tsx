// src/components/HeroComponent.tsx

import React from 'react';

const HeroComponent: React.FC = () => {
  return (
    <div className="bg-gradient-to-r from-[#61AFFE] via-[#9747FF] to-[#EBA781] text-white p-10">
      <div className="max-w-4xl mx-auto text-center">
        <img src="/logo.svg" alt="Owl-E Logo" className="w-24 mx-auto mb-4" />
        <h1 className="text-4xl font-bold mb-2">Create Smarter Workflows with Owl-E</h1>
        <p className="text-xl mb-6">
          Automate 3D model creation, tag images, and much more with our powerful, integrated solution.
        </p>
        <button className="bg-[#60DDA1] text-black font-bold py-3 px-6 rounded-lg">
          Sign Up for Updates
        </button>
      </div>
    </div>
  );
};

export default HeroComponent;
