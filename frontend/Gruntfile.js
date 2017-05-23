module.exports = function (grunt) {
  // Project Configuration
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    eslint: {
      options: {
        configFile: '.eslintrc.json',
        fix: true,
      },
      target: ['*.js', 'public/app/**/*.js'],
    },
  });

  // Load NPM tasks
  grunt.loadNpmTasks('grunt-eslint');

  // Making grunt default to force in order not to break the project.
  grunt.option('force', true);

  // Default task(s).
  grunt.registerTask('default', ['eslint']);
};
