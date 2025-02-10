export default {
    preset: 'ts-jest',
    testEnvironment: 'node',
    transform: {
      "^.+\\.ts$": ['ts-jest', {
        isolatedModules: true,  // Your ts-jest config goes here
      }]  // Use ts-jest for transforming TypeScript files
    },
    moduleNameMapper: {
        // Map your path aliases from tsconfig.json
        '^@/(.*)$': '<rootDir>/src/$1'
      },
    moduleFileExtensions: ['ts', 'js'],
    testMatch: ['**/tests/**/*.test.ts'],  // Define the pattern for test files
    globals: {
    },
  };