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
    prettify: {
      all: {
        expand: true,
        src: ['public/**/*.html'],
      },
    },
    concurrent: {
      tasks: ['nodemon', 'watch'],
      options: {
        logConcurrentOutput: true,
      },
    },
    nodemon: {
      dev: {
        script: 'index.js',
        options: {
          ignore: ['README.md', 'node_modules/**', '.DS_Store'],
          ext: 'js',
          watch: ['index.js'],
          delayTime: 1,
          cwd: __dirname,
        },
      },
    },
    watch: {
      js: {
        files: ['*.js', 'public/app/**/*.js', '!public/app/app.js'],
        tasks: ['eslint', 'concat'],
        options: {
          livereload: true,
        },
      },
      html: {
        files: ['public/**/*.html', '!public/libs/**'],
        options: {
          livereload: true,
        },
      },
    },
    concat: {
      dist: {
        src: ['public/app/app.modules.js', 'public/app/app.routes.js', 'public/app/services/**/*.js', 'public/app/components/**/*.js'],
        dest: 'public/app/app.js',
      },
    },
    'gh-pages': {
      options: {
        base: 'public',
      },
      src: ['**'],
    },
  });

  // Load NPM tasks
  require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

  // Making grunt default to force in order not to break the project.
  grunt.option('force', true);

  // Default task(s).
  grunt.registerTask('default', ['eslint', 'prettify', 'concat', 'concurrent']);
};
